package com.qoobot.agent.investment-advisor-agent.module.customer-profiling;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * customer-profiling Agent Interface
 * Sub-module of 智能投资顾问AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface CustomerProfilingAgent extends Agent {

    /**
     * Execute customer-profiling related task
     *
     * @param task task content
     * @return execution result
     */
    String executeCustomerProfilingTask(String task);

    /**
     * Get customer-profiling status
     *
     * @return status information
     */
    CustomerProfilingAgentStatus getStatus();

    /**
     * CustomerProfilingAgent status
     */
    enum CustomerProfilingAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
