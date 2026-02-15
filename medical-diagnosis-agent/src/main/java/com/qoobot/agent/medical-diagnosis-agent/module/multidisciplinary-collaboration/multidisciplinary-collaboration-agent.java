package com.qoobot.agent.medical-diagnosis-agent.module.multidisciplinary-collaboration;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * multidisciplinary-collaboration Agent Interface
 * Sub-module of 智能医疗诊断AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface MultidisciplinaryCollaborationAgent extends Agent {

    /**
     * Execute multidisciplinary-collaboration related task
     *
     * @param task task content
     * @return execution result
     */
    String executeMultidisciplinaryCollaborationTask(String task);

    /**
     * Get multidisciplinary-collaboration status
     *
     * @return status information
     */
    MultidisciplinaryCollaborationAgentStatus getStatus();

    /**
     * MultidisciplinaryCollaborationAgent status
     */
    enum MultidisciplinaryCollaborationAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
