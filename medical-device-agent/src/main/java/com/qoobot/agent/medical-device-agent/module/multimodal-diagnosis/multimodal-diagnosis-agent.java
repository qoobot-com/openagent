package com.qoobot.agent.medical-device-agent.module.multimodal-diagnosis;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * multimodal-diagnosis Agent Interface
 * Sub-module of 医疗设备协同AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface MultimodalDiagnosisAgent extends Agent {

    /**
     * Execute multimodal-diagnosis related task
     *
     * @param task task content
     * @return execution result
     */
    String executeMultimodalDiagnosisTask(String task);

    /**
     * Get multimodal-diagnosis status
     *
     * @return status information
     */
    MultimodalDiagnosisAgentStatus getStatus();

    /**
     * MultimodalDiagnosisAgent status
     */
    enum MultimodalDiagnosisAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
