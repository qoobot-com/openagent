package com.qoobot.agent.medical-diagnosis-agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Medical Diagnosis Agent启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.qoobot.agent.medical-diagnosis-agent",
        "com.qoobot.agent.core"
})
public class MedicalDiagnosisAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalDiagnosisAgentApplication.class, args);
    }
}
