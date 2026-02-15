package com.qoobot.agent.cross-border-logistics-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Cross Border Logistics Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.cross-border-logistics-agent",
        "com.qoobot.agent.core"
})
public class CrossBorderLogisticsAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrossBorderLogisticsAgentApplication.class, args);
    }
}
