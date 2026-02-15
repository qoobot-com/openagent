package com.qoobot.agent.intelligent-ops-agent.module.cause-analysis;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * cause-analysis Agent Interface
 * Sub-module of 智能运维管理AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface CauseAnalysisAgent extends Agent {

    /**
     * Execute cause-analysis related task
     *
     * @param task task content
     * @return execution result
     */
    String executeCauseAnalysisTask(String task);

    /**
     * Get cause-analysis status
     *
     * @return status information
     */
    CauseAnalysisAgentStatus getStatus();

    /**
     * CauseAnalysisAgent status
     */
    enum CauseAnalysisAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
