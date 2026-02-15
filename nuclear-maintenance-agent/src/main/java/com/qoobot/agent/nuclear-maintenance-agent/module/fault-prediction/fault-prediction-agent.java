package com.qoobot.agent.nuclear-maintenance-agent.module.fault-prediction;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * fault-prediction Agent Interface
 * Sub-module of 核电设备预测性维护AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface FaultPredictionAgent extends Agent {

    /**
     * Execute fault-prediction related task
     *
     * @param task task content
     * @return execution result
     */
    String executeFaultPredictionTask(String task);

    /**
     * Get fault-prediction status
     *
     * @return status information
     */
    FaultPredictionAgentStatus getStatus();

    /**
     * FaultPredictionAgent status
     */
    enum FaultPredictionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
