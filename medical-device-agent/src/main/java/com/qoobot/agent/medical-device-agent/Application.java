package com.qoobot.agent.medical-device-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Medical Device Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.medical-device-agent",
        "com.qoobot.agent.core"
})
public class MedicalDeviceAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalDeviceAgentApplication.class, args);
    }
}
