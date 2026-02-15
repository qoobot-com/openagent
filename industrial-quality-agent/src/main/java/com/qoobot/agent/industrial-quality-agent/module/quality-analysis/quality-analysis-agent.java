package com.qoobot.agent.industrial-quality-agent.module.quality-analysis;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * quality-analysis Agent Interface
 * Sub-module of 工业质检AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface QualityAnalysisAgent extends Agent {

    /**
     * Execute quality-analysis related task
     *
     * @param task task content
     * @return execution result
     */
    String executeQualityAnalysisTask(String task);

    /**
     * Get quality-analysis status
     *
     * @return status information
     */
    QualityAnalysisAgentStatus getStatus();

    /**
     * QualityAnalysisAgent status
     */
    enum QualityAnalysisAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
