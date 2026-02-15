package com.qoobot.agent.construction-safety-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Construction Safety Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.construction-safety-agent",
        "com.qoobot.agent.core"
})
public class ConstructionSafetyAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConstructionSafetyAgentApplication.class, args);
    }
}
