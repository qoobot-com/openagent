package com.qoobot.agent.power-trading-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Power Trading Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.power-trading-agent",
        "com.qoobot.agent.core"
})
public class PowerTradingAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerTradingAgentApplication.class, args);
    }
}
