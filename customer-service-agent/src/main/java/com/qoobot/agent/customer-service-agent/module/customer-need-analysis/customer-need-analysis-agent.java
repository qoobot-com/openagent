package com.qoobot.agent.customer-service-agent.module.customer-need-analysis;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * customer-need-analysis Agent Interface
 * Sub-module of 智能客服AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface CustomerNeedAnalysisAgent extends Agent {

    /**
     * Execute customer-need-analysis related task
     *
     * @param task task content
     * @return execution result
     */
    String executeCustomerNeedAnalysisTask(String task);

    /**
     * Get customer-need-analysis status
     *
     * @return status information
     */
    CustomerNeedAnalysisAgentStatus getStatus();

    /**
     * CustomerNeedAnalysisAgent status
     */
    enum CustomerNeedAnalysisAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
