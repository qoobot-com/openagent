package com.qoobot.agent.power-trading-agent.module.market-perception;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * market-perception Agent Interface
 * Sub-module of 智能电力交易AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface MarketPerceptionAgent extends Agent {

    /**
     * Execute market-perception related task
     *
     * @param task task content
     * @return execution result
     */
    String executeMarketPerceptionTask(String task);

    /**
     * Get market-perception status
     *
     * @return status information
     */
    MarketPerceptionAgentStatus getStatus();

    /**
     * MarketPerceptionAgent status
     */
    enum MarketPerceptionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
