package com.qoobot.agent.core.hybrid;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 混合 AI 服务 - 统一 LangChain4J、Spring AI、Spring AI Alibaba
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HybridAIService {

    private final AIFrameworkSelector frameworkSelector;
    private final AIFrameworkMetrics metrics;
    private final HybridAIProperties properties;

    // Spring AI ChatClient Builder
    private final ChatClient.Builder springAiChatClientBuilder;

    // LangChain4J ChatLanguageModel (懒加载)
    private volatile ChatLanguageModel langchain4jModel;

    /**
     * 简单聊天 - 自动选择框架
     */
    public String chat(String message) {
        return chat(null, null, message);
    }

    /**
     * 带 Agent 上下文的聊天
     */
    public String chat(String agentId, String message) {
        return chat(agentId, null, message);
    }

    /**
     * 完整聊天接口
     */
    public String chat(String agentId, String taskType, String message) {
        return chat(agentId, taskType, message, AIFrameworkSelector.SelectStrategy.ALWAYS);
    }

    /**
     * 指定策略的聊天
     */
    public String chat(String agentId, String taskType, String message,
                      AIFrameworkSelector.SelectStrategy strategy) {
        AIFramework framework = frameworkSelector.selectFramework(agentId, taskType);

        try {
            return chatWithFramework(framework, message);
        } catch (Exception e) {
            log.error("Chat failed with framework: {}, error: {}", framework, e.getMessage());

            // Fallback 到备用框架
            if (properties.getSwitchStrategy() == HybridAIProperties.SwitchStrategy.FALLBACK) {
                AIFramework fallbackFramework = frameworkSelector.selectFallback(framework);
                log.info("Fallback to framework: {}", fallbackFramework);
                return chatWithFramework(fallbackFramework, message);
            }

            throw new RuntimeException("All AI frameworks failed", e);
        }
    }

    /**
     * 使用指定框架聊天
     */
    private String chatWithFramework(AIFramework framework, String message) {
        AIFrameworkMetrics.RequestContext context = metrics.recordRequestStart(framework);

        try {
            String response = switch (framework) {
                case LANGCHAIN4J -> chatWithLangChain4J(message);
                case SPRING_AI -> chatWithSpringAI(message);
                case SPRING_AI_ALIBABA -> chatWithSpringAIAlibaba(message);
            };

            // 记录成功指标
            metrics.recordRequestSuccess(context, estimateTokenCount(message), estimateTokenCount(response));

            log.info("Chat success - Framework: {}, Response length: {}",
                    framework, response.length());

            return response;
        } catch (Exception e) {
            metrics.recordRequestFailure(context, e.getMessage());
            throw e;
        }
    }

    /**
     * LangChain4J 聊天
     */
    private String chatWithLangChain4J(String message) {
        ChatLanguageModel model = getLangChain4JModel();
        return model.generate(message);
    }

    /**
     * Spring AI 聊天
     */
    private String chatWithSpringAI(String message) {
        return springAiChatClientBuilder.build()
                .prompt(message)
                .call()
                .content();
    }

    /**
     * Spring AI Alibaba 聊天
     */
    private String chatWithSpringAIAlibaba(String message) {
        // 使用 Spring AI Alibaba (DashScope)
        return springAiChatClientBuilder.build()
                .prompt(message)
                .call()
                .content();
    }

    /**
     * 带系统提示的聊天
     */
    public String chatWithSystem(String systemPrompt, String userMessage) {
        AIFramework framework = frameworkSelector.selectFramework();
        AIFrameworkMetrics.RequestContext context = metrics.recordRequestStart(framework);

        try {
            String response = switch (framework) {
                case LANGCHAIN4J -> {
                    ChatLanguageModel model = getLangChain4JModel();
                    yield model.generate(systemPrompt, userMessage);
                }
                case SPRING_AI, SPRING_AI_ALIBABA -> {
                    yield springAiChatClientBuilder.build()
                            .system(systemPrompt)
                            .user(userMessage)
                            .call()
                            .content();
                }
            };

            metrics.recordRequestSuccess(context,
                    estimateTokenCount(systemPrompt + userMessage),
                    estimateTokenCount(response));

            return response;
        } catch (Exception e) {
            metrics.recordRequestFailure(context, e.getMessage());
            throw e;
        }
    }

    /**
     * 并行多框架聊天 - 取最快响应
     */
    public String chatParallel(String message) {
        List<AIFramework> frameworks = List.of(
                AIFramework.LANGCHAIN4J,
                AIFramework.SPRING_AI,
                AIFramework.SPRING_AI_ALIBABA
        );

        CompletableFuture<String>[] futures = frameworks.stream()
                .map(framework -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return chatWithFramework(framework, message);
                    } catch (Exception e) {
                        log.warn("Framework {} failed in parallel", framework);
                        return null;
                    }
                }))
                .toArray(CompletableFuture[]::new);

        try {
            // 返回第一个成功完成的响应
            return (String) CompletableFuture.anyOf(futures).get();
        } catch (Exception e) {
            throw new RuntimeException("All parallel requests failed", e);
        }
    }

    /**
     * 聚合多个框架的回答
     */
    public String chatAggregate(String message, int minResponses) {
        List<AIFramework> frameworks = List.of(
                AIFramework.LANGCHAIN4J,
                AIFramework.SPRING_AI,
                AIFramework.SPRING_AI_ALIBABA
        );

        List<String> responses = frameworks.stream()
                .map(framework -> {
                    try {
                        return chatWithFramework(framework, message);
                    } catch (Exception e) {
                        log.warn("Framework {} failed in aggregation", framework);
                        return null;
                    }
                })
                .filter(response -> response != null)
                .toList();

        if (responses.size() < minResponses) {
            throw new RuntimeException("Not enough successful responses");
        }

        // 聚合所有回答
        return aggregateResponses(responses);
    }

    /**
     * 聚合回答
     */
    private String aggregateResponses(List<String> responses) {
        // 简单实现：合并所有回答
        StringBuilder aggregated = new StringBuilder();
        for (int i = 0; i < responses.size(); i++) {
            aggregated.append("[回答 ").append(i + 1).append("]\n");
            aggregated.append(responses.get(i)).append("\n\n");
        }
        return aggregated.toString();
    }

    /**
     * 获取 LangChain4J 模型（懒加载）
     */
    private ChatLanguageModel getLangChain4JModel() {
        if (langchain4jModel == null) {
            synchronized (this) {
                if (langchain4jModel == null) {
                    HybridAIProperties.LangChain4J config = properties.getLangchain4j();
                    langchain4jModel = OpenAiChatModel.builder()
                            .apiKey(config.getOpenai().getApiKey())
                            .baseUrl(config.getOpenai().getBaseUrl())
                            .modelName(config.getOpenai().getModelName())
                            .temperature(config.getOpenai().getTemperature())
                            .maxTokens(config.getOpenai().getMaxTokens())
                            .maxRetries(config.getOpenai().getMaxRetries())
                            .build();
                }
            }
        }
        return langchain4jModel;
    }

    /**
     * 估算 Token 数量
     */
    private int estimateTokenCount(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        // 简单估算：大约 4 个字符 = 1 个 token
        return (int) Math.ceil(text.length() / 4.0);
    }

    /**
     * 获取性能指标
     */
    public AIFrameworkMetrics.FrameworkMetricsSummary getMetrics() {
        return metrics.getSummary();
    }

    /**
     * 根据性能选择最优框架
     */
    public AIFramework getBestFramework() {
        return frameworkSelector.selectBestFramework(metrics);
    }

    /**
     * 根据可靠性选择框架
     */
    public AIFramework getMostReliableFramework() {
        return frameworkSelector.selectMostReliableFramework(metrics);
    }

    /**
     * 重置指标
     */
    public void resetMetrics() {
        metrics.resetAll();
        log.info("All metrics reset");
    }
}
