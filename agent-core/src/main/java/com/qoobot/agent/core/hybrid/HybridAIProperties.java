package com.qoobot.agent.core.hybrid;

import com.qoobot.agent.core.springai.SpringAIProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 混合 AI 架构配置属性
 * 支持 LangChain4J、Spring AI 2.0、Spring AI Alibaba 三种框架
 */
@Data
@Component
@ConfigurationProperties(prefix = "agent.ai")
public class HybridAIProperties {

    /**
     * 默认框架选择: langchain4j, spring-ai, spring-ai-alibaba
     */
    private String defaultFramework = "spring-ai";

    /**
     * 框架切换策略: always, fallback, load-balance, agent-specific
     */
    private SwitchStrategy switchStrategy = SwitchStrategy.FALLBACK;

    /**
     * LangChain4J 配置
     */
    private LangChain4J langchain4j = new LangChain4J();

    /**
     * Spring AI 配置
     */
    private SpringAIConfig springAI = new SpringAIConfig();

    /**
     * Spring AI Alibaba 配置
     */
    private SpringAIAlibaba springAIAlibaba = new SpringAIAlibaba();

    /**
     * Agent 特定配置
     */
    private AgentSpecific agentSpecific = new AgentSpecific();

    /**
     * 监控配置
     */
    private Monitoring monitoring = new Monitoring();

    /**
     * 切换策略枚举
     */
    public enum SwitchStrategy {
        /**
         * 始终使用默认框架
         */
        ALWAYS,

        /**
         * 失败时降级到备用框架
         */
        FALLBACK,

        /**
         * 负载均衡（轮询）
         */
        LOAD_BALANCE,

        /**
         * 根据 Agent 类型选择框架
         */
        AGENT_SPECIFIC,

        /**
         * 根据任务类型智能选择
         */
        TASK_AWARE
    }

    @Data
    public static class LangChain4J {
        /**
         * 是否启用
         */
        private boolean enabled = true;

        /**
         * 默认模型: openai, anthropic, azure, huggingface
         */
        private String defaultModel = "openai";

        /**
         * OpenAI 配置
         */
        private OpenAIConfig openai = new OpenAIConfig();

        /**
         * 权重（负载均衡）
         */
        private double weight = 0.3;

        /**
         * 超时配置
         */
        private TimeoutConfig timeout = new TimeoutConfig();
    }

    @Data
    public static class OpenAIConfig {
        private String apiKey;
        private String baseUrl = "https://api.openai.com/v1";
        private String modelName = "gpt-4o-mini";
        private Double temperature = 0.7;
        private Integer maxTokens = 2000;
        private Integer maxRetries = 3;
    }

    @Data
    public static class SpringAIConfig {
        /**
         * 是否启用
         */
        private boolean enabled = true;

        /**
         * 模型提供商: openai, dashscope
         */
        private String provider = "openai";

        /**
         * 权重（负载均衡）
         */
        private double weight = 0.4;

        /**
         * 超时配置
         */
        private TimeoutConfig timeout = new TimeoutConfig();
    }

    @Data
    public static class SpringAIAlibaba {
        /**
         * 是否启用
         */
        private boolean enabled = true;

        /**
         * 默认模型: qwen-plus, qwen-turbo
         */
        private String modelName = "qwen-plus";

        /**
         * 权重（负载均衡）
         */
        private double weight = 0.3;

        /**
         * 超时配置
         */
        private TimeoutConfig timeout = new TimeoutConfig();
    }

    @Data
    public static class TimeoutConfig {
        private Long connectTimeout = 10000L;  // 10秒
        private Long readTimeout = 60000L;     // 60秒
        private Long writeTimeout = 60000L;    // 60秒
    }

    @Data
    public static class AgentSpecific {
        /**
         * Agent 类型与框架映射
         */
        private java.util.Map<String, String> frameworkMapping = new java.util.HashMap<>();

        /**
         * 任务类型与框架映射
         */
        private java.util.Map<String, String> taskMapping = new java.util.HashMap<>();

        /**
         * 优先级配置
         */
        private java.util.Map<String, Integer> priority = new java.util.HashMap<>();
    }

    @Data
    public static class Monitoring {
        /**
         * 是否启用监控
         */
        private boolean enabled = true;

        /**
         * 性能阈值（毫秒）
         */
        private Long performanceThreshold = 5000L;

        /**
         * 失败重试次数
         */
        private Integer maxRetries = 3;

        /**
         * 熔断器阈值
         */
        private Double circuitBreakerThreshold = 0.5;  // 50% 失败率

        /**
         * 熔断器半开状态请求数
         */
        private Integer circuitBreakerHalfOpenRequests = 5;
    }
}
