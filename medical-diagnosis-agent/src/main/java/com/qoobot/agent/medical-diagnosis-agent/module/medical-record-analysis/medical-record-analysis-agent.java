package com.qoobot.agent.medical-diagnosis-agent.module.medical-record-analysis;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * medical-record-analysis Agent Interface
 * Sub-module of 智能医疗诊断AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface MedicalRecordAnalysisAgent extends Agent {

    /**
     * Execute medical-record-analysis related task
     *
     * @param task task content
     * @return execution result
     */
    String executeMedicalRecordAnalysisTask(String task);

    /**
     * Get medical-record-analysis status
     *
     * @return status information
     */
    MedicalRecordAnalysisAgentStatus getStatus();

    /**
     * MedicalRecordAnalysisAgent status
     */
    enum MedicalRecordAnalysisAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
