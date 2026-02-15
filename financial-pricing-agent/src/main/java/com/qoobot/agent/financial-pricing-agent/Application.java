package com.qoobot.agent.financial-pricing-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Financial Pricing Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.financial-pricing-agent",
        "com.qoobot.agent.core"
})
public class FinancialPricingAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialPricingAgentApplication.class, args);
    }
}
