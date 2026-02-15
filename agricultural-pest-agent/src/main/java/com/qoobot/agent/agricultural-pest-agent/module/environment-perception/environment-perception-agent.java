package com.qoobot.agent.agricultural-pest-agent.module.environment-perception;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * environment-perception Agent Interface
 * Sub-module of 农业病虫害预测AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface EnvironmentPerceptionAgent extends Agent {

    /**
     * Execute environment-perception related task
     *
     * @param task task content
     * @return execution result
     */
    String executeEnvironmentPerceptionTask(String task);

    /**
     * Get environment-perception status
     *
     * @return status information
     */
    EnvironmentPerceptionAgentStatus getStatus();

    /**
     * EnvironmentPerceptionAgent status
     */
    enum EnvironmentPerceptionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
