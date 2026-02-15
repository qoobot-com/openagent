package com.qoobot.agent.supply-chain-agent.module.scenario-simulation;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * scenario-simulation Agent Interface
 * Sub-module of 供应链韧性AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface ScenarioSimulationAgent extends Agent {

    /**
     * Execute scenario-simulation related task
     *
     * @param task task content
     * @return execution result
     */
    String executeScenarioSimulationTask(String task);

    /**
     * Get scenario-simulation status
     *
     * @return status information
     */
    ScenarioSimulationAgentStatus getStatus();

    /**
     * ScenarioSimulationAgent status
     */
    enum ScenarioSimulationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
