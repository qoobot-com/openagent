package com.qoobot.agent.drug-research-agent.module.compliance-audit;

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * compliance-audit Agent Interface
 * Sub-module of 药物研发协同AI Agent系统
 *
 * @author openagent
 * @since 1.0.0
 */
public interface ComplianceAuditAgent extends Agent {

    /**
     * Execute compliance-audit related task
     *
     * @param task task content
     * @return execution result
     */
    String executeComplianceAuditTask(String task);

    /**
     * Get compliance-audit status
     *
     * @return status information
     */
    ComplianceAuditAgentStatus getStatus();

    /**
     * ComplianceAuditAgent status
     */
    enum ComplianceAuditAgentStatus {
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }
}
