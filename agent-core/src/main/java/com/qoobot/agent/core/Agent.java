package com.qoobot.agent.core;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.Map;

/**
 * AI Agent基础接口
 * 所有Agent系统都应实现此接口
 *
 * @author openagent
 * @since 1.0.0
 */
@SystemMessage("""
    你是一个专业的AI Agent助手，负责处理特定领域的任务。
    你需要：
    1. 理解用户需求并提取关键信息
    2. 调用相关工具或服务完成具体任务
    3. 提供清晰、准确的回答和建议
    4. 遇到不确定的情况时，及时请求人类介入
    """)
public interface Agent {

    /**
     * 处理用户请求
     *
     * @param userRequest 用户请求内容
     * @return Agent的响应
     */
    String process(@UserMessage String userRequest);

    /**
     * 处理带参数的请求
     *
     * @param userRequest 用户请求内容
     * @param context     上下文信息
     * @return Agent的响应
     */
    String processWithContext(@UserMessage String userRequest, Map<String, Object> context);

    /**
     * 多轮对话处理
     *
     * @param messages 对话历史
     * @return Agent的响应
     */
    String chat(@V("messages") String messages);

    /**
     * 获取Agent配置信息
     *
     * @return Agent配置
     */
    AgentConfig getConfig();

    /**
     * 检查Agent健康状态
     *
     * @return 健康状态
     */
    AgentHealth checkHealth();
}
