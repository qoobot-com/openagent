package com.qoobot.agent.core;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Agent配置信息
 *
 * @author openagent
 * @since 1.0.0
 */
@Data
@Builder
public class AgentConfig {

    /**
     * Agent唯一标识
     */
    private String agentId;

    /**
     * Agent名称
     */
    private String agentName;

    /**
     * Agent类型
     */
    private AgentType agentType;

    /**
     * Agent描述
     */
    private String description;

    /**
     * Agent版本
     */
    private String version;

    /**
     * 模型配置
     */
    private ModelConfig modelConfig;

    /**
     * 工具配置
     */
    private Map<String, ToolConfig> tools;

    /**
     * MCP工具列表
     */
    private Map<String, String> mcpTools;

    /**
     * 对外接口配置
     */
    private ApiConfig apiConfig;

    /**
     * 安全配置
     */
    private SecurityConfig securityConfig;

    /**
     * 性能配置
     */
    private PerformanceConfig performanceConfig;

    /**
     * Agent类型枚举
     */
    public enum AgentType {
        /**
         * 感知型Agent - 负责数据采集与监控
         */
        PERCEPTION,

        /**
         * 分析型Agent - 负责数据分析与推理
         */
        ANALYSIS,

        /**
         * 决策型Agent - 负责决策与优化
         */
        DECISION,

        /**
         * 执行型Agent - 负责任务执行
         */
        EXECUTION,

        /**
         * 协调型Agent - 负责多Agent协调
         */
        COORDINATOR
    }
}
