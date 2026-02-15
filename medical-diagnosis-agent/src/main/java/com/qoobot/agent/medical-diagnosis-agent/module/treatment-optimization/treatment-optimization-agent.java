package com.qoobot.agent.medical-diagnosis-agent.module.treatment-optimization;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * treatment-optimization Agent Interface
 * Sub-module of 智能医疗诊断AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface TreatmentOptimizationAgent extends Agent {

    /**
     * Execute treatment-optimization related task
     *
     * @param task task content
     * @return execution result
     */
    String executeTreatmentOptimizationTask(String task);

    /**
     * Get treatment-optimization status
     *
     * @return status information
     */
    TreatmentOptimizationAgentStatus getStatus();

    /**
     * TreatmentOptimizationAgent status
     */
    enum TreatmentOptimizationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
