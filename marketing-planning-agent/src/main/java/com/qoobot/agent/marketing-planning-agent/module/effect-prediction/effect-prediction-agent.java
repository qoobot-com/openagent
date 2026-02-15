package com.qoobot.agent.marketing-planning-agent.module.effect-prediction;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * effect-prediction Agent Interface
 * Sub-module of 智能营销策划AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface EffectPredictionAgent extends Agent {

    /**
     * Execute effect-prediction related task
     *
     * @param task task content
     * @return execution result
     */
    String executeEffectPredictionTask(String task);

    /**
     * Get effect-prediction status
     *
     * @return status information
     */
    EffectPredictionAgentStatus getStatus();

    /**
     * EffectPredictionAgent status
     */
    enum EffectPredictionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
