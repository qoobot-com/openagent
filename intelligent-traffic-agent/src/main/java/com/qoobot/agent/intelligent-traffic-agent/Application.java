package com.qoobot.agent.intelligent-traffic-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Intelligent Traffic Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.intelligent-traffic-agent",
        "com.qoobot.agent.core"
})
public class IntelligentTrafficAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentTrafficAgentApplication.class, args);
    }
}
