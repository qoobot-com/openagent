package com.qoobot.agent.industrial-quality-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Industrial Quality Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.industrial-quality-agent",
        "com.qoobot.agent.core"
})
public class IndustrialQualityAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndustrialQualityAgentApplication.class, args);
    }
}
