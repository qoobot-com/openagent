package com.qoobot.agent.investment-advisor-agent.module.dynamic-tuning;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * dynamic-tuning Agent Interface
 * Sub-module of 智能投资顾问AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface DynamicTuningAgent extends Agent {

    /**
     * Execute dynamic-tuning related task
     *
     * @param task task content
     * @return execution result
     */
    String executeDynamicTuningTask(String task);

    /**
     * Get dynamic-tuning status
     *
     * @return status information
     */
    DynamicTuningAgentStatus getStatus();

    /**
     * DynamicTuningAgent status
     */
    enum DynamicTuningAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
