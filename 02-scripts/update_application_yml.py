"""
更新所有Agent系统的application.yml配置为PostgreSQL 16 + pgvector
"""

import os
import glob

# 新的application.yml内容模板
NEW_YML_CONTENT = """server:
  port: 8080
  servlet:
    context-path: /{CONTEXT_PATH}

spring:
  application:
    name: {AGENT_ID}
  profiles:
    active: dev

  # 数据源配置 - PostgreSQL 16
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/openagent_{DB_NAME}
    username: postgres
    password: postgres
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000

  # Redis配置
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
      timeout: 5000ms
      lettuce:
        pool:
          max-active: 8
          max-idle: 8
          min-idle: 0
          max-wait: -1ms

  # Kafka配置
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      acks: all
      retries: 3
    consumer:
      group-id: ${{spring.application.name}}-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer

# MyBatis Plus配置
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  type-aliases-package: com.qoobot.agent.{PACKAGE_NAME}.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl
    default-executor-type: REUSE
  global-config:
    db-config:
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0

# Agent配置
agent:
  id: {AGENT_ID}
  name: {AGENT_NAME}
  description: {AGENT_DESCRIPTION}
  version: 1.0.0

  # 向量数据库配置 - PostgreSQL + pgvector
  vector:
    enabled: true
    dimension: 1536
    index-type: ivfflat
    metric-type: cosine
    index-lists: 100

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
      ttl-seconds: 3600

# Actuator配置
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
      show-components: always
  metrics:
    export:
      prometheus:
        enabled: true
    tags:
      application: ${{spring.application.name}}

# OpenAPI配置
springdoc:
  api-docs:
    path: /v3/api-docs
    enabled: true
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
    tags-sorter: alpha
    operations-sorter: alpha

# 日志配置
logging:
  level:
    root: INFO
    com.qoobot.agent.{PACKAGE_NAME}: DEBUG
    com.pgvector: DEBUG
  pattern:
    console: "%d{{yyyy-MM-dd HH:mm:ss.SSS}} [%thread] %-5level %logger{{36}} - %msg%n"
    file: "%d{{yyyy-MM-dd HH:mm:ss.SSS}} [%thread] %-5level %logger{{36}} - %msg%n"
  file:
    name: logs/{AGENT_ID}.log
    max-size: 100MB
    max-history: 30
"""

# Agent系统映射
AGENT_MAPPING = {
    "drug-research-agent": {
        "name": "Drug Research Agent",
        "description": "药物研发协同AI Agent系统"
    },
    "medical-diagnosis-agent": {
        "name": "Medical Diagnosis Agent",
        "description": "智能医疗诊断AI Agent系统"
    },
    "medical-device-agent": {
        "name": "Medical Device Agent",
        "description": "医疗设备协同AI Agent系统"
    },
    "agricultural-pest-agent": {
        "name": "Agricultural Pest Agent",
        "description": "农业病虫害预测AI Agent系统"
    },
    "cross-border-logistics-agent": {
        "name": "Cross Border Logistics Agent",
        "description": "跨境物流优化AI Agent系统"
    },
    "warehouse-scheduling-agent": {
        "name": "Warehouse Scheduling Agent",
        "description": "智能仓储调度AI Agent系统"
    },
    "nuclear-maintenance-agent": {
        "name": "Nuclear Maintenance Agent",
        "description": "核电设备预测性维护AI Agent系统"
    },
    "power-grid-agent": {
        "name": "Power Grid Agent",
        "description": "电网负荷预测AI Agent系统"
    },
    "power-trading-agent": {
        "name": "Power Trading Agent",
        "description": "智能电力交易AI Agent系统"
    },
    "semiconductor-optimization-agent": {
        "name": "Semiconductor Optimization Agent",
        "description": "半导体工艺优化AI Agent系统"
    },
    "industrial-quality-agent": {
        "name": "Industrial Quality Agent",
        "description": "工业质检AI Agent系统"
    },
    "financial-pricing-agent": {
        "name": "Financial Pricing Agent",
        "description": "金融动态定价AI Agent系统"
    },
    "investment-advisor-agent": {
        "name": "Investment Advisor Agent",
        "description": "智能投资顾问AI Agent系统"
    },
    "construction-safety-agent": {
        "name": "Construction Safety Agent",
        "description": "建筑安全巡检AI Agent系统"
    },
    "supply-chain-agent": {
        "name": "Supply Chain Agent",
        "description": "供应链韧性AI Agent系统"
    },
    "customer-service-agent": {
        "name": "Customer Service Agent",
        "description": "智能客服AI Agent系统"
    },
    "intelligent-traffic-agent": {
        "name": "Intelligent Traffic Agent",
        "description": "智能交通管理AI Agent系统"
    },
    "marketing-planning-agent": {
        "name": "Marketing Planning Agent",
        "description": "智能营销策划AI Agent系统"
    },
    "intelligent-ops-agent": {
        "name": "Intelligent Operations Agent",
        "description": "智能运维管理AI Agent系统"
    }
}


def update_application_yml(agent_id):
    """更新指定Agent的application.yml"""
    if agent_id not in AGENT_MAPPING:
        print(f"  ✗ Unknown agent: {agent_id}")
        return

    agent_info = AGENT_MAPPING[agent_id]
    yml_file = f"d:/05workspaces/openagent/{agent_id}/src/main/resources/application.yml"

    if not os.path.exists(yml_file):
        print(f"  ✗ File not found: {yml_file}")
        return

    # 生成新的配置内容
    content = NEW_YML_CONTENT.format(
        AGENT_ID=agent_id,
        CONTEXT_PATH=agent_id.replace('-', '_'),
        DB_NAME=agent_id.replace('-', '_'),
        PACKAGE_NAME=agent_id,
        AGENT_NAME=agent_info['name'],
        AGENT_DESCRIPTION=agent_info['description']
    )

    # 写入文件
    with open(yml_file, 'w', encoding='utf-8') as f:
        f.write(content)

    print(f"  ✓ Updated: {agent_id}")


def main():
    """主函数"""
    print("=" * 60)
    print("更新application.yml为PostgreSQL 16 + pgvector配置")
    print("=" * 60)
    print()

    base_path = "d:/05workspaces/openagent"
    agent_dirs = [d for d in os.listdir(base_path)
                  if os.path.isdir(os.path.join(base_path, d))
                  and d.endswith('-agent')]

    for agent_id in agent_dirs:
        print(f"Processing: {agent_id}")
        update_application_yml(agent_id)
        print()

    print("=" * 60)
    print(f"总计更新了 {len(agent_dirs)} 个Agent系统的配置")
    print("=" * 60)


if __name__ == "__main__":
    main()
