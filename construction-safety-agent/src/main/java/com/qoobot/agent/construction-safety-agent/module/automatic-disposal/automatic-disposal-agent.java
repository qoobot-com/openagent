package com.qoobot.agent.construction-safety-agent.module.automatic-disposal;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * automatic-disposal Agent Interface
 * Sub-module of 建筑安全巡检AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface AutomaticDisposalAgent extends Agent {

    /**
     * Execute automatic-disposal related task
     *
     * @param task task content
     * @return execution result
     */
    String executeAutomaticDisposalTask(String task);

    /**
     * Get automatic-disposal status
     *
     * @return status information
     */
    AutomaticDisposalAgentStatus getStatus();

    /**
     * AutomaticDisposalAgent status
     */
    enum AutomaticDisposalAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
