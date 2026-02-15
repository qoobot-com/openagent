package com.qoobot.agent.marketing-planning-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Marketing Planning Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.marketing-planning-agent",
        "com.qoobot.agent.core"
})
public class MarketingPlanningAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketingPlanningAgentApplication.class, args);
    }
}
