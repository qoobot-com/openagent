package com.qoobot.agent.core;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Agent健康状态
 *
 * @author openagent
 * @since 1.0.0
 */
@Data
@Builder
public class AgentHealth {

    /**
     * Agent ID
     */
    private String agentId;

    /**
     * 健康状态
     */
    private HealthStatus status;

    /**
     * 响应时间(毫秒)
     */
    private Long responseTime;

    /**
     * 最后检查时间
     */
    private Long lastCheckTime;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 详细指标
     */
    private Map<String, Object> metrics;

    /**
     * 健康状态枚举
     */
    public enum HealthStatus {
        /**
         * 健康
         */
        HEALTHY,

        /**
         * 降级
         */
        DEGRADED,

        /**
         * 不健康
         */
        UNHEALTHY
    }
}
