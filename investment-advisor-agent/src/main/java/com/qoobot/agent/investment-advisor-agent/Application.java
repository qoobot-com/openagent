package com.qoobot.agent.investment-advisor-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Investment Advisor Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.investment-advisor-agent",
        "com.qoobot.agent.core"
})
public class InvestmentAdvisorAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestmentAdvisorAgentApplication.class, args);
    }
}
