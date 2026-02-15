package com.qoobot.agent.nuclear-maintenance-agent.module.equipment-monitoring;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * equipment-monitoring Agent Interface
 * Sub-module of 核电设备预测性维护AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface EquipmentMonitoringAgent extends Agent {

    /**
     * Execute equipment-monitoring related task
     *
     * @param task task content
     * @return execution result
     */
    String executeEquipmentMonitoringTask(String task);

    /**
     * Get equipment-monitoring status
     *
     * @return status information
     */
    EquipmentMonitoringAgentStatus getStatus();

    /**
     * EquipmentMonitoringAgent status
     */
    enum EquipmentMonitoringAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
