package com.qoobot.agent.power-trading-agent.module.trading-strategy;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * trading-strategy Agent Interface
 * Sub-module of 智能电力交易AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface TradingStrategyAgent extends Agent {

    /**
     * Execute trading-strategy related task
     *
     * @param task task content
     * @return execution result
     */
    String executeTradingStrategyTask(String task);

    /**
     * Get trading-strategy status
     *
     * @return status information
     */
    TradingStrategyAgentStatus getStatus();

    /**
     * TradingStrategyAgent status
     */
    enum TradingStrategyAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
