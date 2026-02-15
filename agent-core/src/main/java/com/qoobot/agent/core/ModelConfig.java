package com.qoobot.agent.core;

import lombok.Builder;
import lombok.Data;

/**
 * 模型配置
 *
 * @author openagent
 * @since 1.0.0
 */
@Data
@Builder
public class ModelConfig {

    /**
     * 模型提供商
     */
    private String provider;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型版本
     */
    private String modelVersion;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * API端点
     */
    private String apiEndpoint;

    /**
     * 最大Token数
     */
    private Integer maxTokens;

    /**
     * 温度参数(0-1)
     */
    private Double temperature;

    /**
     * Top-P参数
     */
    private Double topP;

    /**
     * 频率惩罚
     */
    private Double frequencyPenalty;

    /**
     * 存在惩罚
     */
    private Double presencePenalty;

    /**
     * 响应超时(秒)
     */
    private Integer timeout;

    /**
     * 重试次数
     */
    private Integer maxRetries;

    /**
     * 启用流式输出
     */
    private Boolean streaming;
}
