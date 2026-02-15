package com.qoobot.agent.power-grid-agent.module.dispatch-decision;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * dispatch-decision Agent Interface
 * Sub-module of 电网负荷预测AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface DispatchDecisionAgent extends Agent {

    /**
     * Execute dispatch-decision related task
     *
     * @param task task content
     * @return execution result
     */
    String executeDispatchDecisionTask(String task);

    /**
     * Get dispatch-decision status
     *
     * @return status information
     */
    DispatchDecisionAgentStatus getStatus();

    /**
     * DispatchDecisionAgent status
     */
    enum DispatchDecisionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
