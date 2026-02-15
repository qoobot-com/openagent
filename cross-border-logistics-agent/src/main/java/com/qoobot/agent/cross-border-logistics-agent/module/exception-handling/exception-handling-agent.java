package com.qoobot.agent.cross-border-logistics-agent.module.exception-handling;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * exception-handling Agent Interface
 * Sub-module of 跨境物流优化AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface ExceptionHandlingAgent extends Agent {

    /**
     * Execute exception-handling related task
     *
     * @param task task content
     * @return execution result
     */
    String executeExceptionHandlingTask(String task);

    /**
     * Get exception-handling status
     *
     * @return status information
     */
    ExceptionHandlingAgentStatus getStatus();

    /**
     * ExceptionHandlingAgent status
     */
    enum ExceptionHandlingAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
