package com.qoobot.agent.supply-chain-agent.module.supplier-collaboration;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * supplier-collaboration Agent Interface
 * Sub-module of 供应链韧性AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface SupplierCollaborationAgent extends Agent {

    /**
     * Execute supplier-collaboration related task
     *
     * @param task task content
     * @return execution result
     */
    String executeSupplierCollaborationTask(String task);

    /**
     * Get supplier-collaboration status
     *
     * @return status information
     */
    SupplierCollaborationAgentStatus getStatus();

    /**
     * SupplierCollaborationAgent status
     */
    enum SupplierCollaborationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
