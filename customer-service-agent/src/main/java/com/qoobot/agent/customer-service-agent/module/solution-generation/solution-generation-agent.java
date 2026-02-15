package com.qoobot.agent.customer-service-agent.module.solution-generation;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * solution-generation Agent Interface
 * Sub-module of 智能客服AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface SolutionGenerationAgent extends Agent {

    /**
     * Execute solution-generation related task
     *
     * @param task task content
     * @return execution result
     */
    String executeSolutionGenerationTask(String task);

    /**
     * Get solution-generation status
     *
     * @return status information
     */
    SolutionGenerationAgentStatus getStatus();

    /**
     * SolutionGenerationAgent status
     */
    enum SolutionGenerationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
