package com.qoobot.agent.warehouse-scheduling-agent.module.dynamic-scheduling;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * dynamic-scheduling Agent Interface
 * Sub-module of 智能仓储调度AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface DynamicSchedulingAgent extends Agent {

    /**
     * Execute dynamic-scheduling related task
     *
     * @param task task content
     * @return execution result
     */
    String executeDynamicSchedulingTask(String task);

    /**
     * Get dynamic-scheduling status
     *
     * @return status information
     */
    DynamicSchedulingAgentStatus getStatus();

    /**
     * DynamicSchedulingAgent status
     */
    enum DynamicSchedulingAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
