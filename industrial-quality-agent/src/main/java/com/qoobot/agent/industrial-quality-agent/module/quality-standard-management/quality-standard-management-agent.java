package com.qoobot.agent.industrial-quality-agent.module.quality-standard-management;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * quality-standard-management Agent Interface
 * Sub-module of 工业质检AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface QualityStandardManagementAgent extends Agent {

    /**
     * Execute quality-standard-management related task
     *
     * @param task task content
     * @return execution result
     */
    String executeQualityStandardManagementTask(String task);

    /**
     * Get quality-standard-management status
     *
     * @return status information
     */
    QualityStandardManagementAgentStatus getStatus();

    /**
     * QualityStandardManagementAgent status
     */
    enum QualityStandardManagementAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
