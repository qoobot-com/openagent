package com.qoobot.agent.semiconductor-optimization-agent.module.process-perception;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * process-perception Agent Interface
 * Sub-module of 半导体工艺优化AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface ProcessPerceptionAgent extends Agent {

    /**
     * Execute process-perception related task
     *
     * @param task task content
     * @return execution result
     */
    String executeProcessPerceptionTask(String task);

    /**
     * Get process-perception status
     *
     * @return status information
     */
    ProcessPerceptionAgentStatus getStatus();

    /**
     * ProcessPerceptionAgent status
     */
    enum ProcessPerceptionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
