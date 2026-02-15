package com.qoobot.agent.power-trading-agent.module.compliance-monitoring;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * compliance-monitoring Agent Interface
 * Sub-module of 智能电力交易AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface ComplianceMonitoringAgent extends Agent {

    /**
     * Execute compliance-monitoring related task
     *
     * @param task task content
     * @return execution result
     */
    String executeComplianceMonitoringTask(String task);

    /**
     * Get compliance-monitoring status
     *
     * @return status information
     */
    ComplianceMonitoringAgentStatus getStatus();

    /**
     * ComplianceMonitoringAgent status
     */
    enum ComplianceMonitoringAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
