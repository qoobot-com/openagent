package com.qoobot.agent.intelligent-ops-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Intelligent Operations Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.intelligent-ops-agent",
        "com.qoobot.agent.core"
})
public class IntelligentOpsAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentOpsAgentApplication.class, args);
    }
}
