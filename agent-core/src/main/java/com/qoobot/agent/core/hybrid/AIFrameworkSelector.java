package com.qoobot.agent.core.hybrid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AI 框架选择器 - 支持多种切换策略
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AIFrameworkSelector {

    private final HybridAIProperties properties;

    /**
     * 负载均衡计数器
     */
    private final AtomicInteger loadBalanceCounter = new AtomicInteger(0);

    /**
     * 选择 AI 框架
     */
    public AIFramework selectFramework() {
        return selectFramework(null, null);
    }

    /**
     * 根据 Agent 类型选择框架
     */
    public AIFramework selectFramework(String agentId) {
        return selectFramework(agentId, null);
    }

    /**
     * 根据任务类型选择框架
     */
    public AIFramework selectFramework(String agentId, String taskType) {
        HybridAIProperties.SwitchStrategy strategy = properties.getSwitchStrategy();

        return switch (strategy) {
            case ALWAYS -> selectAlwaysFramework();
            case FALLBACK -> selectDefaultFramework();
            case LOAD_BALANCE -> selectLoadBalanceFramework();
            case AGENT_SPECIFIC -> selectAgentSpecificFramework(agentId);
            case TASK_AWARE -> selectTaskAwareFramework(agentId, taskType);
        };
    }

    /**
     * 始终使用默认框架
     */
    private AIFramework selectAlwaysFramework() {
        return AIFramework.fromString(properties.getDefaultFramework());
    }

    /**
     * 选择默认框架
     */
    private AIFramework selectDefaultFramework() {
        return AIFramework.fromString(properties.getDefaultFramework());
    }

    /**
     * 负载均衡选择
     */
    private AIFramework selectLoadBalanceFramework() {
        double langchain4jWeight = properties.getLangchain4j().getWeight();
        double springAIWeight = properties.getSpringAI().getWeight();
        double springAIAlibabaWeight = properties.getSpringAIAlibaba().getWeight();

        // 归一化权重
        double totalWeight = langchain4jWeight + springAIWeight + springAIAlibabaWeight;
        double normalizedLangChain = langchain4jWeight / totalWeight;
        double normalizedSpringAI = springAIWeight / totalWeight;
        double normalizedSpringAIAlibaba = springAIAlibabaWeight / totalWeight;

        // 轮询选择
        int count = loadBalanceCounter.incrementAndGet() % 3;
        return switch (count) {
            case 0 -> {
                if (Math.random() < normalizedLangChain) {
                    yield AIFramework.LANGCHAIN4J;
                }
                yield selectFallback(AIFramework.LANGCHAIN4J);
            }
            case 1 -> {
                if (Math.random() < normalizedSpringAI) {
                    yield AIFramework.SPRING_AI;
                }
                yield selectFallback(AIFramework.SPRING_AI);
            }
            default -> {
                if (Math.random() < normalizedSpringAIAlibaba) {
                    yield AIFramework.SPRING_AI_ALIBABA;
                }
                yield selectFallback(AIFramework.SPRING_AI_ALIBABA);
            }
        };
    }

    /**
     * Agent 特定选择
     */
    private AIFramework selectAgentSpecificFramework(String agentId) {
        if (agentId == null) {
            return selectDefaultFramework();
        }

        String frameworkName = properties.getAgentSpecific()
                .getFrameworkMapping()
                .get(agentId);

        if (frameworkName != null) {
            return AIFramework.fromString(frameworkName);
        }

        return selectDefaultFramework();
    }

    /**
     * 任务感知选择
     */
    private AIFramework selectTaskAwareFramework(String agentId, String taskType) {
        if (taskType == null) {
            return selectAgentSpecificFramework(agentId);
        }

        // 根据任务类型选择最适合的框架
        String frameworkName = properties.getAgentSpecific()
                .getTaskMapping()
                .get(taskType);

        if (frameworkName != null) {
            return AIFramework.fromString(frameworkName);
        }

        // 默认策略
        return selectAgentSpecificFramework(agentId);
    }

    /**
     * 选择备用框架
     */
    public AIFramework selectFallback(AIFramework currentFramework) {
        return switch (currentFramework) {
            case LANGCHAIN4J -> {
                if (properties.getSpringAI().isEnabled()) {
                    yield AIFramework.SPRING_AI;
                }
                yield AIFramework.SPRING_AI_ALIBABA;
            }
            case SPRING_AI -> {
                if (properties.getLangchain4j().isEnabled()) {
                    yield AIFramework.LANGCHAIN4J;
                }
                yield AIFramework.SPRING_AI_ALIBABA;
            }
            case SPRING_AI_ALIBABA -> {
                if (properties.getSpringAI().isEnabled()) {
                    yield AIFramework.SPRING_AI;
                }
                yield AIFramework.LANGCHAIN4J;
            }
        };
    }

    /**
     * 根据性能选择最优框架
     */
    public AIFramework selectBestFramework(AIFrameworkMetrics metrics) {
        // 简单实现：选择平均响应时间最短的框架
        double langchain4jTime = metrics.getAverageResponseTime(AIFramework.LANGCHAIN4J);
        double springAITime = metrics.getAverageResponseTime(AIFramework.SPRING_AI);
        double springAIAlibabaTime = metrics.getAverageResponseTime(AIFramework.SPRING_AI_ALIBABA);

        if (langchain4jTime <= springAITime && langchain4jTime <= springAIAlibabaTime) {
            return AIFramework.LANGCHAIN4J;
        } else if (springAITime <= springAIAlibabaTime) {
            return AIFramework.SPRING_AI;
        } else {
            return AIFramework.SPRING_AI_ALIBABA;
        }
    }

    /**
     * 根据成功率选择框架
     */
    public AIFramework selectMostReliableFramework(AIFrameworkMetrics metrics) {
        double langchain4jRate = metrics.getSuccessRate(AIFramework.LANGCHAIN4J);
        double springAIRate = metrics.getSuccessRate(AIFramework.SPRING_AI);
        double springAIAlibabaRate = metrics.getSuccessRate(AIFramework.SPRING_AI_ALIBABA);

        if (langchain4jRate >= springAIRate && langchain4jRate >= springAIAlibabaRate) {
            return AIFramework.LANGCHAIN4J;
        } else if (springAIRate >= springAIAlibabaRate) {
            return AIFramework.SPRING_AI;
        } else {
            return AIFramework.SPRING_AI_ALIBABA;
        }
    }

    /**
     * 重置负载均衡计数器
     */
    public void resetLoadBalanceCounter() {
        loadBalanceCounter.set(0);
        log.info("Load balance counter reset");
    }
}
