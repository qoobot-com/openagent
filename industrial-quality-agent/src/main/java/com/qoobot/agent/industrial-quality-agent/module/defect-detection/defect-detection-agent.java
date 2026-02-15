package com.qoobot.agent.industrial-quality-agent.module.defect-detection;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * defect-detection Agent Interface
 * Sub-module of 工业质检AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface DefectDetectionAgent extends Agent {

    /**
     * Execute defect-detection related task
     *
     * @param task task content
     * @return execution result
     */
    String executeDefectDetectionTask(String task);

    /**
     * Get defect-detection status
     *
     * @return status information
     */
    DefectDetectionAgentStatus getStatus();

    /**
     * DefectDetectionAgent status
     */
    enum DefectDetectionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
