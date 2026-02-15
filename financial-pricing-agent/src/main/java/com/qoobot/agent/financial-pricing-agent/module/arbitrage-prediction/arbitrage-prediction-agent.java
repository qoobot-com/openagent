package com.qoobot.agent.financial-pricing-agent.module.arbitrage-prediction;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * arbitrage-prediction Agent Interface
 * Sub-module of 金融动态定价AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface ArbitragePredictionAgent extends Agent {

    /**
     * Execute arbitrage-prediction related task
     *
     * @param task task content
     * @return execution result
     */
    String executeArbitragePredictionTask(String task);

    /**
     * Get arbitrage-prediction status
     *
     * @return status information
     */
    ArbitragePredictionAgentStatus getStatus();

    /**
     * ArbitragePredictionAgent status
     */
    enum ArbitragePredictionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
