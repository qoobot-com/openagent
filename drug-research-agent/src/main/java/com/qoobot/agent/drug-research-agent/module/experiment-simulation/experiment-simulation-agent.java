package com.qoobot.agent.drug-research-agent.module.experiment-simulation;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * experiment-simulation Agent Interface
 * Sub-module of 药物研发协同AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface ExperimentSimulationAgent extends Agent {

    /**
     * Execute experiment-simulation related task
     *
     * @param task task content
     * @return execution result
     */
    String executeExperimentSimulationTask(String task);

    /**
     * Get experiment-simulation status
     *
     * @return status information
     */
    ExperimentSimulationAgentStatus getStatus();

    /**
     * ExperimentSimulationAgent status
     */
    enum ExperimentSimulationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
