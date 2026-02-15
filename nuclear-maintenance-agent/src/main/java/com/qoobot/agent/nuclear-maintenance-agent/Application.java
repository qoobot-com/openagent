package com.qoobot.agent.nuclear-maintenance-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Nuclear Maintenance Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.nuclear-maintenance-agent",
        "com.qoobot.agent.core"
})
public class NuclearMaintenanceAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(NuclearMaintenanceAgentApplication.class, args);
    }
}
