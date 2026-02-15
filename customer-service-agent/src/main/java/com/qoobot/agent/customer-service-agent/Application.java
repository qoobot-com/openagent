package com.qoobot.agent.customer-service-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Customer Service Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.customer-service-agent",
        "com.qoobot.agent.core"
})
public class CustomerServiceAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceAgentApplication.class, args);
    }
}
