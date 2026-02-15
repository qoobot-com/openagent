package com.qoobot.agent.drug-research-agent.module.literature-analysis;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * literature-analysis Agent Interface
 * Sub-module of 药物研发协同AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface LiteratureAnalysisAgent extends Agent {

    /**
     * Execute literature-analysis related task
     *
     * @param task task content
     * @return execution result
     */
    String executeLiteratureAnalysisTask(String task);

    /**
     * Get literature-analysis status
     *
     * @return status information
     */
    LiteratureAnalysisAgentStatus getStatus();

    /**
     * LiteratureAnalysisAgent status
     */
    enum LiteratureAnalysisAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
