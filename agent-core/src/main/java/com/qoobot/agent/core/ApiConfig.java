package com.qoobot.agent.core;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * API配置
 *
 * @author openagent
 * @since 1.0.0
 */
@Data
@Builder
public class ApiConfig {

    /**
     * API基础路径
     */
    private String basePath;

    /**
     * API版本
     */
    private String apiVersion;

    /**
     * 启用的接口列表
     */
    private List<ApiEndpoint> endpoints;

    /**
     * 认证方式
     */
    private AuthType authType;

    /**
     * 限流配置
     */
    private RateLimitConfig rateLimit;

    /**
     * 数据库配置
     */
    private DatabaseConfig database;

    /**
     * 数据库配置
     */
    @Data
    @Builder
    public static class DatabaseConfig {
        private String databaseUrl;
        private String databaseUsername;
        private String databasePassword;
        private String driverClassName;
        private Integer maxPoolSize;
        private Integer minIdle;
        private Long connectionTimeout;
        private Long idleTimeout;
        private Long maxLifetime;
    }

    /**
     * API端点配置
     */
    @Data
    @Builder
    public static class ApiEndpoint {
        private String path;
        private String method;
        private String description;
        private Boolean authenticated;
        private Integer timeout;
    }

    /**
     * 认证类型
     */
    public enum AuthType {
        NONE, API_KEY, JWT, OAUTH2
    }

    /**
     * 限流配置
     */
    @Data
    @Builder
    public static class RateLimitConfig {
        private Integer requestsPerSecond;
        private Integer requestsPerMinute;
        private Integer requestsPerHour;
    }
}
