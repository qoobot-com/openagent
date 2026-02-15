package com.qoobot.agent.semiconductor-optimization-agent.module.adaptive-control;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * adaptive-control Agent Interface
 * Sub-module of 半导体工艺优化AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface AdaptiveControlAgent extends Agent {

    /**
     * Execute adaptive-control related task
     *
     * @param task task content
     * @return execution result
     */
    String executeAdaptiveControlTask(String task);

    /**
     * Get adaptive-control status
     *
     * @return status information
     */
    AdaptiveControlAgentStatus getStatus();

    /**
     * AdaptiveControlAgent status
     */
    enum AdaptiveControlAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
