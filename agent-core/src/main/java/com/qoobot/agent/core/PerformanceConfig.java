package com.qoobot.agent.core;

import lombok.Builder;
import lombok.Data;

/**
 * 性能配置
 *
 * @author openagent
 * @since 1.0.0
 */
@Data
@Builder
public class PerformanceConfig {

    /**
     * 最大并发请求数
     */
    private Integer maxConcurrentRequests;

    /**
     * 请求队列大小
     */
    private Integer queueSize;

    /**
     * 线程池大小
     */
    private Integer threadPoolSize;

    /**
     * 缓存配置
     */
    private CacheConfig cache;

    /**
     * 批处理配置
     */
    private BatchConfig batch;

    /**
     * 超时配置
     */
    private TimeoutConfig timeout;

    /**
     * 缓存配置
     */
    @Data
    @Builder
    public static class CacheConfig {
        private Boolean enabled;
        private Long maxSize;
        private Long expireAfterWrite;
        private Long expireAfterAccess;
    }

    /**
     * 批处理配置
     */
    @Data
    @Builder
    public static class BatchConfig {
        private Boolean enabled;
        private Integer batchSize;
        private Long batchTimeout;
    }

    /**
     * 超时配置
     */
    @Data
    @Builder
    public static class TimeoutConfig {
        private Integer connectTimeout;
        private Integer readTimeout;
        private Integer writeTimeout;
    }
}
