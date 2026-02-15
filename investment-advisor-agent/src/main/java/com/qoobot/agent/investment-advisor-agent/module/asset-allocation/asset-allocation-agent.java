package com.qoobot.agent.investment-advisor-agent.module.asset-allocation;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * asset-allocation Agent Interface
 * Sub-module of 智能投资顾问AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface AssetAllocationAgent extends Agent {

    /**
     * Execute asset-allocation related task
     *
     * @param task task content
     * @return execution result
     */
    String executeAssetAllocationTask(String task);

    /**
     * Get asset-allocation status
     *
     * @return status information
     */
    AssetAllocationAgentStatus getStatus();

    /**
     * AssetAllocationAgent status
     */
    enum AssetAllocationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
