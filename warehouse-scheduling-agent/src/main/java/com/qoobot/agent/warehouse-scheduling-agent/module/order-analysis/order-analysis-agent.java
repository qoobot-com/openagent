package com.qoobot.agent.warehouse-scheduling-agent.module.order-analysis;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * order-analysis Agent Interface
 * Sub-module of 智能仓储调度AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface OrderAnalysisAgent extends Agent {

    /**
     * Execute order-analysis related task
     *
     * @param task task content
     * @return execution result
     */
    String executeOrderAnalysisTask(String task);

    /**
     * Get order-analysis status
     *
     * @return status information
     */
    OrderAnalysisAgentStatus getStatus();

    /**
     * OrderAnalysisAgent status
     */
    enum OrderAnalysisAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
