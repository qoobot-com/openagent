package com.qoobot.agent.financial-pricing-agent.module.autonomous-execution;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * autonomous-execution Agent Interface
 * Sub-module of 金融动态定价AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface AutonomousExecutionAgent extends Agent {

    /**
     * Execute autonomous-execution related task
     *
     * @param task task content
     * @return execution result
     */
    String executeAutonomousExecutionTask(String task);

    /**
     * Get autonomous-execution status
     *
     * @return status information
     */
    AutonomousExecutionAgentStatus getStatus();

    /**
     * AutonomousExecutionAgent status
     */
    enum AutonomousExecutionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
