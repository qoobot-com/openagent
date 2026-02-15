package com.qoobot.agent.medical-diagnosis-agent.module.medical-record-analysis.impl;

import com.qoobot.agent.medical-diagnosis-agent.module.medical-record-analysis.MedicalRecordAnalysisAgent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * MedicalRecordAnalysisAgent实现类
 *
 * @author openagent
 * @since 1.0.0
 */
@Slf4j
@Service
@SystemMessage("""{
    You are MedicalRecordAnalysisAgent, responsible for medical-record-analysis related tasks.
    You need to:
    1. Understand user requirements and extract key information
    2. Call relevant tools to complete tasks
    3. Provide clear and accurate results
    }""")
public class MedicalRecordAnalysisAgentImpl implements MedicalRecordAnalysisAgent {

    private final AgentConfig config;
    private MedicalRecordAnalysisAgentStatus status = MedicalRecordAnalysisAgentStatus.IDLE;

    public MedicalRecordAnalysisAgentImpl(AgentConfig config) {
        this.config = config;
    }

    @Override
    public String process(@UserMessage String userRequest) {
        log.info("Processing request: {}", userRequest);
        // TODO: Implement specific processing logic
        return "Request processed by MedicalRecordAnalysisAgent";
    }

    @Override
    public String processWithContext(String userRequest, java.util.Map<String, Object> context) {
        log.info("Processing request with context: {}", userRequest);
        // TODO: Implement context-aware processing logic
        return "Request with context processed by MedicalRecordAnalysisAgent";
    }

    @Override
    public String chat(String messages) {
        log.info("Chatting with messages: {}", messages);
        // TODO: Implement multi-turn dialogue logic
        return "Chat response from MedicalRecordAnalysisAgent";
    }

    @Override
    public String executeMedicalRecordAnalysisTask(String task) {
        log.info("Executing task: {}", task);
        this.status = MedicalRecordAnalysisAgentStatus.RUNNING;
        try {
            // TODO: Implement specific task execution logic
            String result = "Task completed successfully";
            this.status = MedicalRecordAnalysisAgentStatus.COMPLETED;
            return result;
        } catch (Exception e) {
            log.error("Task execution failed", e);
            this.status = MedicalRecordAnalysisAgentStatus.ERROR;
            throw new RuntimeException("Task execution failed", e);
        }
    }

    @Override
    public MedicalRecordAnalysisAgentStatus getStatus() {
        return this.status;
    }

    @Override
    public AgentConfig getConfig() {
        return this.config;
    }

    @Override
    public AgentHealth checkHealth() {
        return AgentHealth.builder()
                .agentId(config.getAgentId())
                .status(AgentHealth.HealthStatus.HEALTHY)
                .responseTime(System.currentTimeMillis())
                .lastCheckTime(System.currentTimeMillis())
                .build();
    }
}
