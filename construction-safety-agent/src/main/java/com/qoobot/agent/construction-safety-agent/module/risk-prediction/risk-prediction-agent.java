package com.qoobot.agent.construction-safety-agent.module.risk-prediction;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * risk-prediction Agent Interface
 * Sub-module of 建筑安全巡检AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface RiskPredictionAgent extends Agent {

    /**
     * Execute risk-prediction related task
     *
     * @param task task content
     * @return execution result
     */
    String executeRiskPredictionTask(String task);

    /**
     * Get risk-prediction status
     *
     * @return status information
     */
    RiskPredictionAgentStatus getStatus();

    /**
     * RiskPredictionAgent status
     */
    enum RiskPredictionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
