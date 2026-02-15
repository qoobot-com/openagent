package com.qoobot.agent.intelligent-ops-agent.module.auto-repair;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * auto-repair Agent Interface
 * Sub-module of 智能运维管理AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface AutoRepairAgent extends Agent {

    /**
     * Execute auto-repair related task
     *
     * @param task task content
     * @return execution result
     */
    String executeAutoRepairTask(String task);

    /**
     * Get auto-repair status
     *
     * @return status information
     */
    AutoRepairAgentStatus getStatus();

    /**
     * AutoRepairAgent status
     */
    enum AutoRepairAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
