package com.qoobot.agent.intelligent-traffic-agent.module.signal-optimization;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * signal-optimization Agent Interface
 * Sub-module of 智能交通管理AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface SignalOptimizationAgent extends Agent {

    /**
     * Execute signal-optimization related task
     *
     * @param task task content
     * @return execution result
     */
    String executeSignalOptimizationTask(String task);

    /**
     * Get signal-optimization status
     *
     * @return status information
     */
    SignalOptimizationAgentStatus getStatus();

    /**
     * SignalOptimizationAgent status
     */
    enum SignalOptimizationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
