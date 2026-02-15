package com.qoobot.agent.customer-service-agent.module.cross-system-query;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * cross-system-query Agent Interface
 * Sub-module of 智能客服AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface CrossSystemQueryAgent extends Agent {

    /**
     * Execute cross-system-query related task
     *
     * @param task task content
     * @return execution result
     */
    String executeCrossSystemQueryTask(String task);

    /**
     * Get cross-system-query status
     *
     * @return status information
     */
    CrossSystemQueryAgentStatus getStatus();

    /**
     * CrossSystemQueryAgent status
     */
    enum CrossSystemQueryAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
