package com.qoobot.agent.construction-safety-agent.module.environment-perception.impl;

import com.qoobot.agent.construction-safety-agent.module.environment-perception.EnvironmentPerceptionAgent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * EnvironmentPerceptionAgent实现类
 *
 * @author openagent
 * @since 1.0.0
 */
@Slf4j
@Service
@SystemMessage("""{
    You are EnvironmentPerceptionAgent, responsible for environment-perception related tasks.
    You need to:
    1. Understand user requirements and extract key information
    2. Call relevant tools to complete tasks
    3. Provide clear and accurate results
    }""")
public class EnvironmentPerceptionAgentImpl implements EnvironmentPerceptionAgent {

    private final AgentConfig config;
    private EnvironmentPerceptionAgentStatus status = EnvironmentPerceptionAgentStatus.IDLE;

    public EnvironmentPerceptionAgentImpl(AgentConfig config) {
        this.config = config;
    }

    @Override
    public String process(@UserMessage String userRequest) {
        log.info("Processing request: {}", userRequest);
        // TODO: Implement specific processing logic
        return "Request processed by EnvironmentPerceptionAgent";
    }

    @Override
    public String processWithContext(String userRequest, java.util.Map<String, Object> context) {
        log.info("Processing request with context: {}", userRequest);
        // TODO: Implement context-aware processing logic
        return "Request with context processed by EnvironmentPerceptionAgent";
    }

    @Override
    public String chat(String messages) {
        log.info("Chatting with messages: {}", messages);
        // TODO: Implement multi-turn dialogue logic
        return "Chat response from EnvironmentPerceptionAgent";
    }

    @Override
    public String executeEnvironmentPerceptionTask(String task) {
        log.info("Executing task: {}", task);
        this.status = EnvironmentPerceptionAgentStatus.RUNNING;
        try {
            // TODO: Implement specific task execution logic
            String result = "Task completed successfully";
            this.status = EnvironmentPerceptionAgentStatus.COMPLETED;
            return result;
        } catch (Exception e) {
            log.error("Task execution failed", e);
            this.status = EnvironmentPerceptionAgentStatus.ERROR;
            throw new RuntimeException("Task execution failed", e);
        }
    }

    @Override
    public EnvironmentPerceptionAgentStatus getStatus() {
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
