package com.qoobot.agent.medical-device-agent.module.device-monitoring;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * device-monitoring Agent Interface
 * Sub-module of 医疗设备协同AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface DeviceMonitoringAgent extends Agent {

    /**
     * Execute device-monitoring related task
     *
     * @param task task content
     * @return execution result
     */
    String executeDeviceMonitoringTask(String task);

    /**
     * Get device-monitoring status
     *
     * @return status information
     */
    DeviceMonitoringAgentStatus getStatus();

    /**
     * DeviceMonitoringAgent status
     */
    enum DeviceMonitoringAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
