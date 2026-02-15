package com.qoobot.agent.supply-chain-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Supply Chain Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.supply-chain-agent",
        "com.qoobot.agent.core"
})
public class SupplyChainAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplyChainAgentApplication.class, args);
    }
}
