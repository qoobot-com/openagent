package com.qoobot.agent.intelligent-traffic-agent.module.traffic-perception;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * traffic-perception Agent Interface
 * Sub-module of 智能交通管理AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface TrafficPerceptionAgent extends Agent {

    /**
     * Execute traffic-perception related task
     *
     * @param task task content
     * @return execution result
     */
    String executeTrafficPerceptionTask(String task);

    /**
     * Get traffic-perception status
     *
     * @return status information
     */
    TrafficPerceptionAgentStatus getStatus();

    /**
     * TrafficPerceptionAgent status
     */
    enum TrafficPerceptionAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
