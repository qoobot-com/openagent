package com.qoobot.agent.core.hybrid;

/**
 * AI 框架枚举
 */
public enum AIFramework {

    /**
     * LangChain4J
     */
    LANGCHAIN4J("langchain4j", "LangChain4J"),

    /**
     * Spring AI 2.0
     */
    SPRING_AI("spring-ai", "Spring AI 2.0"),

    /**
     * Spring AI Alibaba
     */
    SPRING_AI_ALIBABA("spring-ai-alibaba", "Spring AI Alibaba");

    private final String code;
    private final String displayName;

    AIFramework(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * 从字符串解析
     */
    public static AIFramework fromString(String framework) {
        if (framework == null || framework.isEmpty()) {
            return SPRING_AI; // 默认值
        }

        return switch (framework.toLowerCase().trim()) {
            case "langchain4j" -> LANGCHAIN4J;
            case "spring-ai" -> SPRING_AI;
            case "spring-ai-alibaba", "dashscope", "alibaba" -> SPRING_AI_ALIBABA;
            default -> SPRING_AI;
        };
    }

    /**
     * 是否启用
     */
    public boolean isEnabled(HybridAIProperties properties) {
        return switch (this) {
            case LANGCHAIN4J -> properties.getLangchain4j().isEnabled();
            case SPRING_AI -> properties.getSpringAI().isEnabled();
            case SPRING_AI_ALIBABA -> properties.getSpringAIAlibaba().isEnabled();
        };
    }

    /**
     * 获取权重
     */
    public double getWeight(HybridAIProperties properties) {
        return switch (this) {
            case LANGCHAIN4J -> properties.getLangchain4j().getWeight();
            case SPRING_AI -> properties.getSpringAI().getWeight();
            case SPRING_AI_ALIBABA -> properties.getSpringAIAlibaba().getWeight();
        };
    }
}
