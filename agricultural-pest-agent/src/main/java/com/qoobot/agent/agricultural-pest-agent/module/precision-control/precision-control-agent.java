package com.qoobot.agent.agricultural-pest-agent.module.precision-control;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * precision-control Agent Interface
 * Sub-module of 农业病虫害预测AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface PrecisionControlAgent extends Agent {

    /**
     * Execute precision-control related task
     *
     * @param task task content
     * @return execution result
     */
    String executePrecisionControlTask(String task);

    /**
     * Get precision-control status
     *
     * @return status information
     */
    PrecisionControlAgentStatus getStatus();

    /**
     * PrecisionControlAgent status
     */
    enum PrecisionControlAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
