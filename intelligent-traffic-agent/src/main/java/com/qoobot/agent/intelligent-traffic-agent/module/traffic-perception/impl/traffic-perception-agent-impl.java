package com.qoobot.agent.intelligent-traffic-agent.module.traffic-perception.impl;

import com.qoobot.agent.intelligent-traffic-agent.module.traffic-perception.TrafficPerceptionAgent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TrafficPerceptionAgent实现类
 *
 * @author openagent
 * @since 1.0.0
 */
@Slf4j
@Service
@SystemMessage("""{
    You are TrafficPerceptionAgent, responsible for traffic-perception related tasks.
    You need to:
    1. Understand user requirements and extract key information
    2. Call relevant tools to complete tasks
    3. Provide clear and accurate results
    }""")
public class TrafficPerceptionAgentImpl implements TrafficPerceptionAgent {

    private final AgentConfig config;
    private TrafficPerceptionAgentStatus status = TrafficPerceptionAgentStatus.IDLE;

    public TrafficPerceptionAgentImpl(AgentConfig config) {
        this.config = config;
    }

    @Override
    public String process(@UserMessage String userRequest) {
        log.info("Processing request: {}", userRequest);
        // TODO: Implement specific processing logic
        return "Request processed by TrafficPerceptionAgent";
    }

    @Override
    public String processWithContext(String userRequest, java.util.Map<String, Object> context) {
        log.info("Processing request with context: {}", userRequest);
        // TODO: Implement context-aware processing logic
        return "Request with context processed by TrafficPerceptionAgent";
    }

    @Override
    public String chat(String messages) {
        log.info("Chatting with messages: {}", messages);
        // TODO: Implement multi-turn dialogue logic
        return "Chat response from TrafficPerceptionAgent";
    }

    @Override
    public String executeTrafficPerceptionTask(String task) {
        log.info("Executing task: {}", task);
        this.status = TrafficPerceptionAgentStatus.RUNNING;
        try {
            // TODO: Implement specific task execution logic
            String result = "Task completed successfully";
            this.status = TrafficPerceptionAgentStatus.COMPLETED;
            return result;
        } catch (Exception e) {
            log.error("Task execution failed", e);
            this.status = TrafficPerceptionAgentStatus.ERROR;
            throw new RuntimeException("Task execution failed", e);
        }
    }

    @Override
    public TrafficPerceptionAgentStatus getStatus() {
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
