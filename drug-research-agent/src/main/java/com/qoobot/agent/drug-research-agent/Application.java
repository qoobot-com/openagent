package com.qoobot.agent.drug-research-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Drug Research Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.drug-research-agent",
        "com.qoobot.agent.core"
})
public class DrugResearchAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrugResearchAgentApplication.class, args);
    }
}
