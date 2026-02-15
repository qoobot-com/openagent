package com.qoobot.agent.power-grid-agent.module.multimodal-prediction;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * multimodal-prediction Agent Interface
 * Sub-module of 电网负荷预测AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface MultimodalPredictionAgent extends Agent {

    /**
     * Execute multimodal-prediction related task
     *
     * @param task task content
     * @return execution result
     */
    String executeMultimodalPredictionTask(String task);

    /**
     * Get multimodal-prediction status
     *
     * @return status information
     */
    MultimodalPredictionAgentStatus getStatus();

    /**
     * MultimodalPredictionAgent status
     */
    enum MultimodalPredictionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
