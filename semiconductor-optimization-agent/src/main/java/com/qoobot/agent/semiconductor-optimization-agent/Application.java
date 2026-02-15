package com.qoobot.agent.semiconductor-optimization-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Semiconductor Optimization Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.semiconductor-optimization-agent",
        "com.qoobot.agent.core"
})
public class SemiconductorOptimizationAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemiconductorOptimizationAgentApplication.class, args);
    }
}
