package com.qoobot.agent.marketing-planning-agent.module.market-insight;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * market-insight Agent Interface
 * Sub-module of 智能营销策划AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface MarketInsightAgent extends Agent {

    /**
     * Execute market-insight related task
     *
     * @param task task content
     * @return execution result
     */
    String executeMarketInsightTask(String task);

    /**
     * Get market-insight status
     *
     * @return status information
     */
    MarketInsightAgentStatus getStatus();

    /**
     * MarketInsightAgent status
     */
    enum MarketInsightAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
