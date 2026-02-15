package com.qoobot.agent.intelligent-traffic-agent.module.emergency-disposal;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * emergency-disposal Agent Interface
 * Sub-module of 智能交通管理AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface EmergencyDisposalAgent extends Agent {

    /**
     * Execute emergency-disposal related task
     *
     * @param task task content
     * @return execution result
     */
    String executeEmergencyDisposalTask(String task);

    /**
     * Get emergency-disposal status
     *
     * @return status information
     */
    EmergencyDisposalAgentStatus getStatus();

    /**
     * EmergencyDisposalAgent status
     */
    enum EmergencyDisposalAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
