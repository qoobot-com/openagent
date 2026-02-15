package com.qoobot.agent.core;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 安全配置
 *
 * @author openagent
 * @since 1.0.0
 */
@Data
@Builder
public class SecurityConfig {

    /**
     * 是否启用认证
     */
    private Boolean authenticationEnabled;

    /**
     * 是否启用授权
     */
    private Boolean authorizationEnabled;

    /**
     * 允许的IP列表
     */
    private List<String> allowedIps;

    /**
     * 敏感数据加密
     */
    private EncryptionConfig encryption;

    /**
     * 审计日志
     */
    private AuditLogConfig auditLog;

    /**
     * 数据脱敏
     */
    private DataMaskingConfig dataMasking;

    /**
     * 加密配置
     */
    @Data
    @Builder
    public static class EncryptionConfig {
        private Boolean enabled;
        private String algorithm;
        private String key;
    }

    /**
     * 审计日志配置
     */
    @Data
    @Builder
    public static class AuditLogConfig {
        private Boolean enabled;
        private LogLevel level;
        private Boolean includeUserActions;
        private Boolean includeApiCalls;
    }

    /**
     * 日志级别
     */
    public enum LogLevel {
        INFO, WARN, ERROR
    }

    /**
     * 数据脱敏配置
     */
    @Data
    @Builder
    public static class DataMaskingConfig {
        private Boolean enabled;
        private List<String> sensitiveFields;
        private MaskingStrategy strategy;
    }

    /**
     * 脱敏策略
     */
    public enum MaskingStrategy {
        PARTIAL, HASH, ENCRYPT
    }
}
