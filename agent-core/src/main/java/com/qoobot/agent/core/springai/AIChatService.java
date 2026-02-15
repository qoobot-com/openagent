package com.qoobot.agent.core.springai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AI 聊天服务 - 统一封装 Spring AI 聊天能力
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AIChatService {

    private final ChatClient.Builder chatClientBuilder;
    private final PromptTemplate ragPromptTemplate;
    private final PromptTemplate agentTaskPromptTemplate;

    /**
     * 简单文本聊天
     */
    public String chat(String message) {
        log.info("AI Chat - Message: {}", message);
        return chatClientBuilder.build().prompt()
                .user(message)
                .call()
                .content();
    }

    /**
     * 带 System Message 的聊天
     */
    public String chatWithSystem(String systemPrompt, String userMessage) {
        log.info("AI Chat with System - User: {}", userMessage);
        return chatClientBuilder.build().prompt()
                .system(systemPrompt)
                .user(userMessage)
                .call()
                .content();
    }

    /**
     * RAG 聊天（带上下文检索）
     */
    public String chatWithRAG(String question, String context) {
        log.info("AI Chat with RAG - Question: {}", question);
        Map<String, Object> variables = Map.of(
                "question", question,
                "context", context
        );
        Prompt prompt = ragPromptTemplate.create(variables);
        return chatClientBuilder.build().prompt(prompt)
                .call()
                .content();
    }

    /**
     * Agent 任务执行
     */
    public String executeAgentTask(String agentType, String taskDescription,
                                    String availableTools, String context) {
        log.info("Agent Task Execution - Type: {}, Task: {}", agentType, taskDescription);
        Map<String, Object> variables = Map.of(
                "agent_type", agentType,
                "task_description", taskDescription,
                "available_tools", availableTools,
                "context", context
        );
        Prompt prompt = agentTaskPromptTemplate.create(variables);
        return chatClientBuilder.build().prompt(prompt)
                .call()
                .content();
    }

    /**
     * 多轮对话
     */
    public String chatMultiTurn(List<Message> messages) {
        log.info("AI Multi-turn Chat - Messages count: {}", messages.size());
        return chatClientBuilder.build().prompt(new Prompt(messages))
                .call()
                .content();
    }

    /**
     * 流式聊天
     */
    public void chatStream(String message, AIStreamCallback callback) {
        log.info("AI Stream Chat - Message: {}", message);
        chatClientBuilder.build().prompt()
                .user(message)
                .stream()
                .content()
                .subscribe(callback::onContent, callback::onError, callback::onComplete);
    }

    /**
     * 文本总结
     */
    public String summarize(String content, int maxLength) {
        log.info("AI Summarize - Content length: {}, Max length: {}", content.length(), maxLength);
        PromptTemplate summaryTemplate = new PromptTemplate("""
                请对以下内容进行简洁明了的总结：

                {content}

                要求：
                1. 保留关键信息
                2. 使用条理清晰的结构
                3. 字数控制在 {max_length} 字以内
                """);
        Map<String, Object> variables = Map.of(
                "content", content,
                "max_length", maxLength
        );
        return chatClientBuilder.build().prompt(summaryTemplate.create(variables))
                .call()
                .content();
    }

    /**
     * JSON 格式化响应
     */
    public String chatWithJsonFormat(String message) {
        log.info("AI Chat with JSON format - Message: {}", message);
        String systemPrompt = "请以 JSON 格式返回结果，不要包含其他任何文字说明。";
        return chatClientBuilder.build().prompt()
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
    }

    /**
     * 自定义 Prompt 模板
     */
    public String chatWithTemplate(String template, Map<String, Object> variables) {
        log.info("AI Chat with Template - Template length: {}", template.length());
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(variables);
        return chatClientBuilder.build().prompt(prompt)
                .call()
                .content();
    }

    /**
     * 流式回调接口
     */
    @FunctionalInterface
    public interface AIStreamCallback {
        void onContent(String content);

        default void onError(Throwable error) {
            log.error("AI Stream Error", error);
        }

        default void onComplete() {
            log.info("AI Stream Completed");
        }
    }
}
