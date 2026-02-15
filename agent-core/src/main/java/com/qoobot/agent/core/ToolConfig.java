package com.qoobot.agent.core;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 工具配置
 *
 * @author openagent
 * @since 1.0.0
 */
@Data
@Builder
public class ToolConfig {

    /**
     * 工具名称
     */
    private String name;

    /**
     * 工具描述
     */
    private String description;

    /**
     * 工具类型
     */
    private ToolType type;

    /**
     * 工具类路径
     */
    private String className;

    /**
     * 工具方法
     */
    private String method;

    /**
     * MCP工具ID
     */
    private String mcpToolId;

    /**
     * 工具参数配置
     */
    private Map<String, Object> parameters;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 超时时间(秒)
     */
    private Integer timeout;

    /**
     * 最大重试次数
     */
    private Integer maxRetries;

    /**
     * 工具类型枚举
     */
    public enum ToolType {
        /**
         * 数据库查询工具
         */
        DATABASE,

        /**
         * HTTP请求工具
         */
        HTTP,

        /**
         * 文件操作工具
         */
        FILE,

        /**
         * 计算工具
         */
        CALCULATION,

        /**
         * 搜索工具
         */
        SEARCH,

        /**
         * MCP工具
         */
        MCP,

        /**
         * 自定义工具
         */
        CUSTOM
    }
}
