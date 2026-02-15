package com.qoobot.agent.warehouse-scheduling-agent.module.path-planning;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * path-planning Agent Interface
 * Sub-module of 智能仓储调度AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface PathPlanningAgent extends Agent {

    /**
     * Execute path-planning related task
     *
     * @param task task content
     * @return execution result
     */
    String executePathPlanningTask(String task);

    /**
     * Get path-planning status
     *
     * @return status information
     */
    PathPlanningAgentStatus getStatus();

    /**
     * PathPlanningAgent status
     */
    enum PathPlanningAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
