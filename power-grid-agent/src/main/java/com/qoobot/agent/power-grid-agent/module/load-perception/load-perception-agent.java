package com.qoobot.agent.power-grid-agent.module.load-perception;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * load-perception Agent Interface
 * Sub-module of 电网负荷预测AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface LoadPerceptionAgent extends Agent {

    /**
     * Execute load-perception related task
     *
     * @param task task content
     * @return execution result
     */
    String executeLoadPerceptionTask(String task);

    /**
     * Get load-perception status
     *
     * @return status information
     */
    LoadPerceptionAgentStatus getStatus();

    /**
     * LoadPerceptionAgent status
     */
    enum LoadPerceptionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
