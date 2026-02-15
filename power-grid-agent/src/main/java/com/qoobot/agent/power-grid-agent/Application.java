package com.qoobot.agent.power-grid-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Power Grid Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.power-grid-agent",
        "com.qoobot.agent.core"
})
public class PowerGridAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerGridAgentApplication.class, args);
    }
}
