package com.qoobot.agent.supply-chain-agent.module.risk-perception;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * risk-perception Agent Interface
 * Sub-module of 供应链韧性AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface RiskPerceptionAgent extends Agent {

    /**
     * Execute risk-perception related task
     *
     * @param task task content
     * @return execution result
     */
    String executeRiskPerceptionTask(String task);

    /**
     * Get risk-perception status
     *
     * @return status information
     */
    RiskPerceptionAgentStatus getStatus();

    /**
     * RiskPerceptionAgent status
     */
    enum RiskPerceptionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
