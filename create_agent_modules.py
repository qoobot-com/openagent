"""
AI Agent系统模块批量生成脚本
为20个AI Agent系统创建标准化的项目结构
"""

import os
import json

# Agent系统列表配置
AGENT_SYSTEMS = [
    {
        "id": "drug-research-agent",
        "name": "Drug Research Agent",
        "description": "药物研发协同AI Agent系统",
        "modules": [
            "literature-analysis",  # 文献分析Agent
            "experiment-simulation",  # 实验模拟Agent
            "compliance-audit"  # 合规审计Agent
        ]
    },
    {
        "id": "medical-diagnosis-agent",
        "name": "Medical Diagnosis Agent",
        "description": "智能医疗诊断AI Agent系统",
        "modules": [
            "medical-record-analysis",  # 病历分析Agent
            "multidisciplinary-collaboration",  # 多学科协作Agent
            "treatment-optimization"  # 治疗方案优化Agent
        ]
    },
    {
        "id": "medical-device-agent",
        "name": "Medical Device Agent",
        "description": "医疗设备协同AI Agent系统",
        "modules": [
            "device-monitoring",  # 设备状态监测Agent
            "multimodal-diagnosis",  # 多模态诊断Agent
            "maintenance-decision"  # 维护决策Agent
        ]
    },
    {
        "id": "agricultural-pest-agent",
        "name": "Agricultural Pest Agent",
        "description": "农业病虫害预测AI Agent系统",
        "modules": [
            "environment-perception",  # 环境感知Agent
            "pest-prediction",  # 病虫害预测Agent
            "precision-control"  # 精准防控Agent
        ]
    },
    {
        "id": "cross-border-logistics-agent",
        "name": "Cross Border Logistics Agent",
        "description": "跨境物流优化AI Agent系统",
        "modules": [
            "dynamic-route-planning",  # 动态路径规划Agent
            "exception-handling",  # 异常处理Agent
            "cost-optimization"  # 成本优化Agent
        ]
    },
    {
        "id": "warehouse-scheduling-agent",
        "name": "Warehouse Scheduling Agent",
        "description": "智能仓储调度AI Agent系统",
        "modules": [
            "order-analysis",  # 订单分析Agent
            "path-planning",  # 路径规划Agent
            "dynamic-scheduling"  # 动态调度Agent
        ]
    },
    {
        "id": "nuclear-maintenance-agent",
        "name": "Nuclear Maintenance Agent",
        "description": "核电设备预测性维护AI Agent系统",
        "modules": [
            "equipment-monitoring",  # 设备状态监测Agent
            "fault-prediction",  # 故障预测Agent
            "maintenance-decision"  # 维修决策Agent
        ]
    },
    {
        "id": "power-grid-agent",
        "name": "Power Grid Agent",
        "description": "电网负荷预测AI Agent系统",
        "modules": [
            "load-perception",  # 负荷感知Agent
            "multimodal-prediction",  # 多模态预测Agent
            "dispatch-decision"  # 调度决策Agent
        ]
    },
    {
        "id": "power-trading-agent",
        "name": "Power Trading Agent",
        "description": "智能电力交易AI Agent系统",
        "modules": [
            "market-perception",  # 市场感知Agent
            "trading-strategy",  # 交易策略生成Agent
            "compliance-monitoring"  # 合规监控Agent
        ]
    },
    {
        "id": "semiconductor-optimization-agent",
        "name": "Semiconductor Optimization Agent",
        "description": "半导体工艺优化AI Agent系统",
        "modules": [
            "process-perception",  # 工艺参数感知Agent
            "multi-objective-optimization",  # 多目标优化Agent
            "adaptive-control"  # 自适应控制Agent
        ]
    },
    {
        "id": "industrial-quality-agent",
        "name": "Industrial Quality Agent",
        "description": "工业质检AI Agent系统",
        "modules": [
            "defect-detection",  # 缺陷检测Agent
            "quality-standard-management",  # 质检标准管理Agent
            "quality-analysis"  # 质量分析Agent
        ]
    },
    {
        "id": "financial-pricing-agent",
        "name": "Financial Pricing Agent",
        "description": "金融动态定价AI Agent系统",
        "modules": [
            "market-perception",  # 市场感知Agent
            "arbitrage-prediction",  # 套利机会预测Agent
            "autonomous-execution"  # 自主执行Agent
        ]
    },
    {
        "id": "investment-advisor-agent",
        "name": "Investment Advisor Agent",
        "description": "智能投资顾问AI Agent系统",
        "modules": [
            "customer-profiling",  # 客户画像Agent
            "asset-allocation",  # 资产配置Agent
            "dynamic-tuning"  # 动态调优Agent
        ]
    },
    {
        "id": "construction-safety-agent",
        "name": "Construction Safety Agent",
        "description": "建筑安全巡检AI Agent系统",
        "modules": [
            "environment-perception",  # 环境感知Agent
            "risk-prediction",  # 风险预测Agent
            "automatic-disposal"  # 自动处置Agent
        ]
    },
    {
        "id": "supply-chain-agent",
        "name": "Supply Chain Agent",
        "description": "供应链韧性AI Agent系统",
        "modules": [
            "risk-perception",  # 风险感知Agent
            "scenario-simulation",  # 多方案推演Agent
            "supplier-collaboration"  # 供应商协同Agent
        ]
    },
    {
        "id": "customer-service-agent",
        "name": "Customer Service Agent",
        "description": "智能客服AI Agent系统",
        "modules": [
            "customer-need-analysis",  # 客户需求分析Agent
            "cross-system-query",  # 跨系统查询Agent
            "solution-generation"  # 解决方案生成Agent
        ]
    },
    {
        "id": "intelligent-traffic-agent",
        "name": "Intelligent Traffic Agent",
        "description": "智能交通管理AI Agent系统",
        "modules": [
            "traffic-perception",  # 交通状态感知Agent
            "signal-optimization",  # 信号优化Agent
            "emergency-disposal"  # 应急处置Agent
        ]
    },
    {
        "id": "marketing-planning-agent",
        "name": "Marketing Planning Agent",
        "description": "智能营销策划AI Agent系统",
        "modules": [
            "market-insight",  # 市场洞察Agent
            "planning-generation",  # 策划生成Agent
            "effect-prediction"  # 效果预测Agent
        ]
    },
    {
        "id": "intelligent-ops-agent",
        "name": "Intelligent Operations Agent",
        "description": "智能运维管理AI Agent系统",
        "modules": [
            "fault-perception",  # 故障感知Agent
            "cause-analysis",  # 原因分析Agent
            "auto-repair"  # 自动修复Agent
        ]
    }
]


def create_directory(base_path, dir_path):
    """创建目录"""
    full_path = os.path.join(base_path, dir_path)
    os.makedirs(full_path, exist_ok=True)
    print(f"✓ Created directory: {full_path}")


def create_file(base_path, file_path, content):
    """创建文件"""
    full_path = os.path.join(base_path, file_path)
    dir_name = os.path.dirname(full_path)
    os.makedirs(dir_name, exist_ok=True)
    with open(full_path, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"✓ Created file: {full_path}")


def generate_pom_xml(agent_system):
    """生成pom.xml文件"""
    agent_id = agent_system["id"]
    name = agent_system["name"]
    description = agent_system["description"]

    pom_content = f"""<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.qoobot</groupId>
        <artifactId>agent-systems</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <artifactId>{agent_id}</artifactId>
    <name>{name}</name>
    <description>{description}</description>

    <dependencies>
        <!-- Agent Core -->
        <dependency>
            <groupId>com.qoobot</groupId>
            <artifactId>agent-core</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>

        <!-- Spring Boot Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- MyBatis Plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
        </dependency>

        <!-- Redis -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <!-- Kafka -->
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- SpringDoc OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
"""
    return pom_content


def generate_application_yml(agent_system):
    """生成application.yml配置文件"""
    agent_id = agent_system["id"]
    name = agent_system["name"]

    yml_content = f"""server:
  port: 8080
  servlet:
    context-path: /{agent_id.replace('-', '_')}

spring:
  application:
    name: {agent_id}
  profiles:
    active: dev

  # 数据源配置
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/openagent_{agent_id.replace('-', '_')}
    username: postgres
    password: postgres
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5

  # Redis配置
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
      timeout: 5000ms

  # Kafka配置
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: ${{spring.application.name}}-group
      auto-offset-reset: earliest

# MyBatis Plus配置
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  type-aliases-package: com.qoobot.agent.{agent_id}.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl

# Agent配置
agent:
  id: {agent_id}
  name: {name}
  description: {agent_system["description"]}
  version: 1.0.0

  # 模型配置
  model:
    provider: openai
    model-name: gpt-4
    max-tokens: 2000
    temperature: 0.7

  # 工具配置
  tools:
    enabled: true
    mcp:
      enabled: true
      endpoint: http://localhost:8080/mcp

  # 性能配置
  performance:
    max-concurrent-requests: 100
    thread-pool-size: 20
    cache:
      enabled: true
      max-size: 1000

# Actuator配置
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

# OpenAPI配置
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true

# 日志配置
logging:
  level:
    root: INFO
    com.qoobot.agent.{agent_id}: DEBUG
"""
    return yml_content


def generate_agent_interface(agent_system, module):
    """生成Agent接口类"""
    agent_id = agent_system["id"]
    module_name = module
    class_name = ''.join(word.capitalize() for word in module_name.split('-')) + 'Agent'

    interface_content = f"""package com.qoobot.agent.{agent_id}.module.{module_name};

import com.qoobot.agent.core.Agent;
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;

/**
 * {module_name} Agent Interface
 * Sub-module of {agent_system["description"]}
 *
 * @author openagent
 * @since 1.0.0
 */
public interface {class_name} extends Agent {{

    /**
     * Execute {module_name} related task
     *
     * @param task task content
     * @return execution result
     */
    String execute{class_name.replace('Agent', '')}Task(String task);

    /**
     * Get {module_name} status
     *
     * @return status information
     */
    {class_name}Status getStatus();

    /**
     * {class_name} status
     */
    enum {class_name}Status {{
        IDLE, RUNNING, PAUSED, COMPLETED, ERROR
    }}
}}
"""
    return interface_content


def generate_agent_impl(agent_system, module):
    """生成Agent实现类"""
    agent_id = agent_system["id"]
    module_name = module
    class_name = ''.join(word.capitalize() for word in module_name.split('-')) + 'Agent'

    impl_content = f"""package com.qoobot.agent.{agent_id}.module.{module_name}.impl;

import com.qoobot.agent.{agent_id}.module.{module_name}.{class_name};
import com.qoobot.agent.core.AgentConfig;
import com.qoobot.agent.core.AgentHealth;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * {class_name}实现类
 *
 * @author openagent
 * @since 1.0.0
 */
@Slf4j
@Service
@SystemMessage(\"""{{
    You are {class_name}, responsible for {module_name} related tasks.
    You need to:
    1. Understand user requirements and extract key information
    2. Call relevant tools to complete tasks
    3. Provide clear and accurate results
    }}\""")
public class {class_name}Impl implements {class_name} {{

    private final AgentConfig config;
    private {class_name}Status status = {class_name}Status.IDLE;

    public {class_name}Impl(AgentConfig config) {{
        this.config = config;
    }}

    @Override
    public String process(@UserMessage String userRequest) {{
        log.info("Processing request: {{}}", userRequest);
        // TODO: Implement specific processing logic
        return "Request processed by {class_name}";
    }}

    @Override
    public String processWithContext(String userRequest, java.util.Map<String, Object> context) {{
        log.info("Processing request with context: {{}}", userRequest);
        // TODO: Implement context-aware processing logic
        return "Request with context processed by {class_name}";
    }}

    @Override
    public String chat(String messages) {{
        log.info("Chatting with messages: {{}}", messages);
        // TODO: Implement multi-turn dialogue logic
        return "Chat response from {class_name}";
    }}

    @Override
    public String execute{class_name.replace('Agent', '')}Task(String task) {{
        log.info("Executing task: {{}}", task);
        this.status = {class_name}Status.RUNNING;
        try {{
            // TODO: Implement specific task execution logic
            String result = "Task completed successfully";
            this.status = {class_name}Status.COMPLETED;
            return result;
        }} catch (Exception e) {{
            log.error("Task execution failed", e);
            this.status = {class_name}Status.ERROR;
            throw new RuntimeException("Task execution failed", e);
        }}
    }}

    @Override
    public {class_name}Status getStatus() {{
        return this.status;
    }}

    @Override
    public AgentConfig getConfig() {{
        return this.config;
    }}

    @Override
    public AgentHealth checkHealth() {{
        return AgentHealth.builder()
                .agentId(config.getAgentId())
                .status(AgentHealth.HealthStatus.HEALTHY)
                .responseTime(System.currentTimeMillis())
                .lastCheckTime(System.currentTimeMillis())
                .build();
    }}
}}
"""
    return impl_content


def generate_main_application_class(agent_system):
    """生成主应用启动类"""
    agent_id = agent_system["id"]
    class_name = ''.join(word.capitalize() for word in agent_id.split('-')) + 'Application'

    main_class_content = f"""package com.qoobot.agent.{agent_id};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * {agent_system["name"]}启动类
 *
 * @author openagent
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = {{
        "com.qoobot.agent.{agent_id}",
        "com.qoobot.agent.core"
}})
public class {class_name} {{

    public static void main(String[] args) {{
        SpringApplication.run({class_name}.class, args);
    }}
}}
"""
    return main_class_content


def generate_readme(agent_system):
    """生成README文件"""
    readme_content = f"""# {agent_system["name"]}

{agent_system["description"]}

## 模块列表

"""

    for i, module in enumerate(agent_system["modules"], 1):
        module_name = ' '.join(word.capitalize() for word in module.split('-'))
        readme_content += f"""### {i}. {module_name} Agent

- **业务设计**: 处理{module_name}相关业务逻辑
- **应用设计**: 提供{module_name}相关API接口
- **数据设计**: 管理{module_name}相关数据
- **技术设计**: 使用MCP协议进行系统集成

"""

    readme_content += """
## 技术栈

- Spring Boot 3.5.10
- LangChain4J 0.34.0
- MyBatis Plus 3.5.7
- PostgreSQL
- Redis
- Kafka
- MCP协议

## 快速开始

### 构建项目

```bash
mvn clean install
```

### 启动服务

```bash
mvn spring-boot:run
```

### 访问API

- Swagger UI: http://localhost:8080/swagger-ui.html
- Actuator: http://localhost:8080/actuator/health

## API文档

详细API文档请参考Swagger UI。

## 配置说明

配置文件位于 `src/main/resources/application.yml`。

## 开发指南

请参考[开发文档](../../docs/垂直领域AI Agent商业设计.md)获取详细信息。

## 许可证

MIT License
"""
    return readme_content


def main():
    """主函数"""
    base_path = "d:/05workspaces/openagent/agent-systems"

    print("=" * 60)
    print("AI Agent系统模块批量生成脚本")
    print("=" * 60)
    print()

    for agent_system in AGENT_SYSTEMS:
        agent_id = agent_system["id"]
        agent_path = os.path.join(base_path, agent_id)

        print(f"正在创建: {agent_id} - {agent_system['name']}")
        print("-" * 60)

        # 创建目录结构
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}")
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/config")
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/controller")
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/service")
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/service/impl")
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/repository")
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/entity")
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/mapper")
        create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/module")
        create_directory(base_path, f"{agent_id}/src/main/resources")
        create_directory(base_path, f"{agent_id}/src/main/resources/mapper")
        create_directory(base_path, f"{agent_id}/src/test/java/com/qoobot/agent/{agent_id}")

        # 创建pom.xml
        create_file(base_path, f"{agent_id}/pom.xml", generate_pom_xml(agent_system))

        # 创建application.yml
        create_file(base_path, f"{agent_id}/src/main/resources/application.yml",
                    generate_application_yml(agent_system))

        # 创建主应用类
        create_file(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/Application.java",
                    generate_main_application_class(agent_system))

        # 创建README
        create_file(base_path, f"{agent_id}/README.md", generate_readme(agent_system))

        # 为每个子模块创建Agent接口和实现
        for module in agent_system["modules"]:
            module_path = os.path.join(agent_path, "src/main/java/com/qoobot/agent",
                                       agent_id, "module", module)
            impl_path = os.path.join(module_path, "impl")

            # 创建模块目录
            create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/module/{module}")
            create_directory(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/module/{module}/impl")

            # 创建Agent接口
            create_file(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/module/{module}/{module}-agent.java",
                        generate_agent_interface(agent_system, module))

            # 创建Agent实现类
            create_file(base_path, f"{agent_id}/src/main/java/com/qoobot/agent/{agent_id}/module/{module}/impl/{module}-agent-impl.java",
                        generate_agent_impl(agent_system, module))

        print(f"✓ 完成: {agent_id}")
        print()

    print("=" * 60)
    print(f"总计创建了 {len(AGENT_SYSTEMS)} 个Agent系统")
    print(f"总计创建了 {sum(len(s['modules']) for s in AGENT_SYSTEMS)} 个子模块")
    print("=" * 60)


if __name__ == "__main__":
    main()
