package com.qoobot.agent.marketing-planning-agent.module.planning-generation;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * planning-generation Agent Interface
 * Sub-module of 智能营销策划AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface PlanningGenerationAgent extends Agent {

    /**
     * Execute planning-generation related task
     *
     * @param task task content
     * @return execution result
     */
    String executePlanningGenerationTask(String task);

    /**
     * Get planning-generation status
     *
     * @return status information
     */
    PlanningGenerationAgentStatus getStatus();

    /**
     * PlanningGenerationAgent status
     */
    enum PlanningGenerationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
