package com.qoobot.agent.cross-border-logistics-agent.module.cost-optimization;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * cost-optimization Agent Interface
 * Sub-module of 跨境物流优化AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface CostOptimizationAgent extends Agent {

    /**
     * Execute cost-optimization related task
     *
     * @param task task content
     * @return execution result
     */
    String executeCostOptimizationTask(String task);

    /**
     * Get cost-optimization status
     *
     * @return status information
     */
    CostOptimizationAgentStatus getStatus();

    /**
     * CostOptimizationAgent status
     */
    enum CostOptimizationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
