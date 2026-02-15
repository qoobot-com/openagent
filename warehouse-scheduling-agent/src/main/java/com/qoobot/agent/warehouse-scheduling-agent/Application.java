package com.qoobot.agent.warehouse-scheduling-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Warehouse Scheduling Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.warehouse-scheduling-agent",
        "com.qoobot.agent.core"
})
public class WarehouseSchedulingAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseSchedulingAgentApplication.class, args);
    }
}
