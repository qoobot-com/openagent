package com.qoobot.agent.semiconductor-optimization-agent.module.multi-objective-optimization;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * multi-objective-optimization Agent Interface
 * Sub-module of 半导体工艺优化AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface MultiObjectiveOptimizationAgent extends Agent {

    /**
     * Execute multi-objective-optimization related task
     *
     * @param task task content
     * @return execution result
     */
    String executeMultiObjectiveOptimizationTask(String task);

    /**
     * Get multi-objective-optimization status
     *
     * @return status information
     */
    MultiObjectiveOptimizationAgentStatus getStatus();

    /**
     * MultiObjectiveOptimizationAgent status
     */
    enum MultiObjectiveOptimizationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
