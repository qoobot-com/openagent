package com.qoobot.agent.agricultural-pest-agent.module.pest-prediction;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * pest-prediction Agent Interface
 * Sub-module of 农业病虫害预测AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface PestPredictionAgent extends Agent {

    /**
     * Execute pest-prediction related task
     *
     * @param task task content
     * @return execution result
     */
    String executePestPredictionTask(String task);

    /**
     * Get pest-prediction status
     *
     * @return status information
     */
    PestPredictionAgentStatus getStatus();

    /**
     * PestPredictionAgent status
     */
    enum PestPredictionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
