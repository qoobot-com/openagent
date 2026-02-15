package com.qoobot.agent.medical-device-agent.module.maintenance-decision;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * maintenance-decision Agent Interface
 * Sub-module of 医疗设备协同AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface MaintenanceDecisionAgent extends Agent {

    /**
     * Execute maintenance-decision related task
     *
     * @param task task content
     * @return execution result
     */
    String executeMaintenanceDecisionTask(String task);

    /**
     * Get maintenance-decision status
     *
     * @return status information
     */
    MaintenanceDecisionAgentStatus getStatus();

    /**
     * MaintenanceDecisionAgent status
     */
    enum MaintenanceDecisionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
