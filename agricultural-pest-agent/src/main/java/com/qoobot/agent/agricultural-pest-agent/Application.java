package com.qoobot.agent.agricultural-pest-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Agricultural Pest Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.agricultural-pest-agent",
        "com.qoobot.agent.core"
})
public class AgriculturalPestAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgriculturalPestAgentApplication.class, args);
    }
}
