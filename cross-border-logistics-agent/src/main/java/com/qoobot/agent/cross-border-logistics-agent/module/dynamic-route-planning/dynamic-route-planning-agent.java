package com.qoobot.agent.cross-border-logistics-agent.module.dynamic-route-planning;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * dynamic-route-planning Agent Interface
 * Sub-module of 跨境物流优化AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface DynamicRoutePlanningAgent extends Agent {

    /**
     * Execute dynamic-route-planning related task
     *
     * @param task task content
     * @return execution result
     */
    String executeDynamicRoutePlanningTask(String task);

    /**
     * Get dynamic-route-planning status
     *
     * @return status information
     */
    DynamicRoutePlanningAgentStatus getStatus();

    /**
     * DynamicRoutePlanningAgent status
     */
    enum DynamicRoutePlanningAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
