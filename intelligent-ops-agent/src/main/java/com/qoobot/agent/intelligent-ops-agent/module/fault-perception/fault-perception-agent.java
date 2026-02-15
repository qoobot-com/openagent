package com.qoobot.agent.intelligent-ops-agent.module.fault-perception;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * fault-perception Agent Interface
 * Sub-module of 智能运维管理AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface FaultPerceptionAgent extends Agent {

    /**
     * Execute fault-perception related task
     *
     * @param task task content
     * @return execution result
     */
    String executeFaultPerceptionTask(String task);

    /**
     * Get fault-perception status
     *
     * @return status information
     */
    FaultPerceptionAgentStatus getStatus();

    /**
     * FaultPerceptionAgent status
     */
    enum FaultPerceptionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
