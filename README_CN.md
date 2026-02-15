# OpenAgent

<div align="center">

![Version](https://img.shields.io/badge/version-1.0.0--SNAPSHOT-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-green)
![Java](https://img.shields.io/badge/Java-21-orange)
![License](https://img.shields.io/badge/license-MIT-yellow)

**23个垂直领域 AI Agent 企业级解决方案**

基于 Spring Boot + LangChain4J + Spring AI 2.0 + Spring AI Alibaba 的混合 AI 架构

</div>

---

## 📖 项目简介

OpenAgent 是一套完整的垂直领域 AI Agent 系统，涵盖医疗健康、农业、物流、能源、金融、制造等23个行业场景。采用**混合 AI 架构**，无缝集成三大主流 AI 框架：

- **LangChain4J** - 轻量级、高性能，适合复杂工作流编排
- **Spring AI 2.0** - Spring 生态原生，企业级应用首选
- **Spring AI Alibaba** - 国内大模型深度适配，支持阿里云 DashScope

---

## 🎯 核心特性

### 混合 AI 架构
- ✅ 三框架无缝切换与融合
- ✅ 智能框架选择与负载均衡
- ✅ 自动容错与降级机制
- ✅ 并行调用与答案聚合

### 统一服务层
- ✅ `HybridAIService` - 统一聊天接口
- ✅ `HybridRAGService` - 混合检索增强生成
- ✅ `AIEmbeddingService` - 向量嵌入服务

### 企业级能力
- ✅ PostgreSQL + pgvector 向量存储
- ✅ Redis 缓存与会话管理
- ✅ Kafka 消息队列
- ✅ MyBatis Plus 数据持久化
- ✅ Actuator 监控与健康检查
- ✅ OpenAPI/Swagger 文档

---

## 📁 项目结构

```
openagent/
├── 01-docs/                           # 项目文档
│   ├── AI Agent项目结构说明.md
│   ├── Spring-AI集成指南.md
│   ├── 混合AI架构集成指南.md
│   ├── PostgreSQL向量数据库配置指南.md
│   └── ...
│
├── 02-scripts/                         # 脚本目录
│   ├── db-init-*.sql                  # 数据库初始化脚本
│   └── ...
│
├── agent-core/                        # 核心框架模块
│   ├── src/main/java/com/qoobot/agent/core/
│   │   ├── langchain4j/              # LangChain4J 集成
│   │   ├── springai/                 # Spring AI 集成
│   │   ├── hybrid/                   # 混合架构服务
│   │   └── ...
│   └── pom.xml
│
├── drug-research-agent/               # 1. 药物研发协同 Agent
├── medical-diagnosis-agent/           # 2. 智能医疗诊断 Agent
├── medical-device-agent/              # 3. 医疗设备协同 Agent
├── agricultural-pest-agent/           # 4. 农业病虫害预测 Agent
├── cross-border-logistics-agent/      # 5. 跨境物流优化 Agent
├── nuclear-maintenance-agent/         # 6. 核电设备预测性维护 Agent
├── power-grid-agent/                  # 7. 电网负荷预测 Agent
├── semiconductor-optimization-agent/  # 8. 半导体工艺优化 Agent
├── financial-pricing-agent/           # 9. 金融动态定价 Agent
├── warehouse-scheduling-agent/        # 10. 智能仓储调度 Agent
├── construction-safety-agent/         # 11. 建筑安全巡检 Agent
├── supply-chain-agent/                # 12. 供应链韧性 Agent
├── customer-service-agent/            # 13. 智能客服 Agent
├── industrial-quality-agent/          # 14. 工业质检 Agent
├── intelligent-traffic-agent/         # 15. 智能交通管理 Agent
├── marketing-planning-agent/          # 16. 智能营销策划 Agent
├── intelligent-ops-agent/             # 17. 智能运维管理 Agent
├── investment-advisor-agent/          # 18. 智能投资顾问 Agent
├── power-trading-agent/               # 19. 智能电力交易 Agent
├── legal-agent/                       # 20. 法律咨询 Agent ⭐
├── education-agent/                   # 21. 教育培训 Agent ⭐
├── retail-agent/                      # 22. 零售电商 Agent ⭐
├── hr-agent/                          # 23. 人力资源 Agent ⭐
│
├── pom.xml                            # 父 POM 配置
├── docker-compose.yml                 # Docker 编排配置
├── README.md                          # 英文文档
└── README_CN.md                       # 本文档
```

---

## 🏗️ 技术栈

### 核心框架
| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 21 | 编程语言 |
| Spring Boot | 3.5.10 | 应用框架 |
| Maven | 3.9.0+ | 构建工具 |

### AI/LLM 框架
| 框架 | 版本 | 说明 |
|------|------|------|
| LangChain4J | 0.34.0 | 轻量级 AI 编排框架 |
| Spring AI | 1.0.0-M5 | Spring 原生 AI 框架 |
| Spring AI Alibaba | 1.0.0-M5.2 | 阿里云 DashScope 集成 |
| OpenAI API | - | GPT-4 / GPT-3.5 |
| 阿里云 DashScope | - | 通义千问 / Qwen |

### 数据存储
| 技术 | 版本 | 用途 |
|------|------|------|
| PostgreSQL | 16+ | 主数据库 + 向量存储 (pgvector) |
| MySQL | 8.0+ | 备选数据库 |
| Redis | 7.0+ | 缓存 / 会话管理 |
| MongoDB | 6.0+ | 文档存储 |
| Neo4j | 5.0+ | 知识图谱 |
| InfluxDB | 2.0+ | 时序数据库 |
| Milvus | 2.3+ | 向量数据库（可选） |

### 消息与队列
| 技术 | 版本 | 用途 |
|------|------|------|
| Apache Kafka | 3.5+ | 消息队列 |
| RabbitMQ | 3.12+ | 任务队列 |

### 数据访问
| 技术 | 版本 | 说明 |
|------|------|------|
| MyBatis Plus | 3.5.7 | ORM 框架 |
| Spring Data | - | Redis / Mongo / Neo4j |

### 监控与可观测性
| 技术 | 版本 | 用途 |
|------|------|------|
| Spring Boot Actuator | 3.x | 监控端点 |
| Micrometer | 1.12+ | 指标收集 |
| Prometheus | 2.47+ | 指标存储 |
| Zipkin | 2.25+ | 链路追踪 |

### API 文档
| 技术 | 版本 | 用途 |
|------|------|------|
| SpringDoc OpenAPI | 2.3.0 | API 文档生成 |

---

## 🚀 快速开始

### 1. 环境要求

- JDK 21+
- Maven 3.9.0+
- PostgreSQL 16+ (带 pgvector 扩展)
- Redis 7.0+
- Docker & Docker Compose (可选)

### 2. 克隆项目

```bash
git clone https://github.com/qoobot-com/openagent.git
cd openagent
```

### 3. 启动基础服务

使用 Docker Compose 快速启动 PostgreSQL、Redis、Kafka：

```bash
docker-compose up -d
```

### 4. 初始化数据库

```bash
# 方式一：批量初始化所有数据库
cd 02-scripts
for file in db-init-*.sql; do
  echo "Initializing $file..."
  psql -U postgres -h localhost -f "$file"
done

# 方式二：初始化单个数据库
psql -U postgres -h localhost -f db-init-drug-research-agent.sql
```

### 5. 配置环境变量

```bash
# LangChain4J OpenAI
export LANGCHAIN4J_OPENAI_API_KEY=sk-...

# Spring AI OpenAI
export OPENAI_API_KEY=sk-...

# Spring AI Alibaba (国内部署)
export DASHSCOPE_API_KEY=sk-...

# PostgreSQL
export DB_USERNAME=postgres
export DB_PASSWORD=123456
```

### 6. 编译项目

```bash
# 编译所有模块
mvn clean install

# 编译特定模块
cd drug-research-agent
mvn clean install
```

### 7. 启动 Agent

```bash
# 启动药物研发 Agent
cd drug-research-agent
mvn spring-boot:run

# 或使用 IDE 运行 Application.java
```

### 8. 访问服务

| 服务 | 地址 |
|------|------|
| Swagger UI | http://localhost:8080/drug-research-agent/swagger-ui.html |
| 健康检查 | http://localhost:8080/drug-research-agent/actuator/health |
| Actuator | http://localhost:8080/drug-research-agent/actuator |

---

## 📦 Agent 列表

| 序号 | Agent | 端口 | 路径 | 数据库 | 说明 |
|------|-------|------|------|--------|------|
| 1 | drug-research-agent | 8080 | /drug-research-agent | openagent_drug_research_agent | 药物研发协同 |
| 2 | medical-diagnosis-agent | 8080 | /medical-diagnosis-agent | openagent_medical_diagnosis_agent | 智能医疗诊断 |
| 3 | medical-device-agent | 8080 | /medical-device-agent | openagent_medical_device_agent | 医疗设备协同 |
| 4 | agricultural-pest-agent | 8080 | /agricultural-pest-agent | openagent_agricultural_pest_agent | 农业病虫害预测 |
| 5 | cross-border-logistics-agent | 8080 | /cross-border-logistics-agent | openagent_cross_border_logistics_agent | 跨境物流优化 |
| 6 | nuclear-maintenance-agent | 8080 | /nuclear-maintenance-agent | openagent_nuclear_maintenance_agent | 核电设备预测性维护 |
| 7 | power-grid-agent | 8080 | /power-grid-agent | openagent_power_grid_agent | 电网负荷预测 |
| 8 | semiconductor-optimization-agent | 8080 | /semiconductor-optimization-agent | openagent_semiconductor_optimization_agent | 半导体工艺优化 |
| 9 | financial-pricing-agent | 8080 | /financial-pricing-agent | openagent_financial_pricing_agent | 金融动态定价 |
| 10 | warehouse-scheduling-agent | 8080 | /warehouse-scheduling-agent | openagent_warehouse_scheduling_agent | 智能仓储调度 |
| 11 | construction-safety-agent | 8080 | /construction-safety-agent | openagent_construction_safety_agent | 建筑安全巡检 |
| 12 | supply-chain-agent | 8080 | /supply-chain-agent | openagent_supply_chain_agent | 供应链韧性 |
| 13 | customer-service-agent | 8080 | /customer-service-agent | openagent_customer_service_agent | 智能客服 |
| 14 | industrial-quality-agent | 8080 | /industrial-quality-agent | openagent_industrial_quality_agent | 工业质检 |
| 15 | intelligent-traffic-agent | 8080 | /intelligent-traffic-agent | openagent_intelligent_traffic_agent | 智能交通管理 |
| 16 | marketing-planning-agent | 8080 | /marketing-planning-agent | openagent_marketing_planning_agent | 智能营销策划 |
| 17 | intelligent-ops-agent | 8080 | /intelligent-ops-agent | openagent_intelligent_ops_agent | 智能运维管理 |
| 18 | investment-advisor-agent | 8080 | /investment-advisor-agent | openagent_investment_advisor_agent | 智能投资顾问 |
| 19 | power-trading-agent | 8080 | /power-trading-agent | openagent_power_trading_agent | 智能电力交易 |
| 20 | legal-agent | 8201 | /legal-agent | openagent_legal_agent | 法律咨询 ⭐ |
| 21 | education-agent | 8202 | /education-agent | openagent_education_agent | 教育培训 ⭐ |
| 22 | retail-agent | 8203 | /retail-agent | openagent_retail_agent | 零售电商 ⭐ |
| 23 | hr-agent | 8204 | /hr-agent | openagent_hr_agent | 人力资源 ⭐ |

---

## 💻 使用示例

### 示例 1：使用混合 AI 服务

```java
@Service
public class YourAgentService {

    @Autowired
    private HybridAIService hybridAI;

    @Autowired
    private HybridRAGService hybridRAG;

    // 简单聊天（自动选择框架）
    public String chat(String message) {
        return hybridAI.chat("your-agent-id", message);
    }

    // RAG 查询
    public String query(String query) {
        return hybridRAG.retrieveAndGenerate(query, "your-agent-id");
    }

    // 并行调用（最快响应）
    public String fastQuery(String question) {
        return hybridAI.chatParallel(question);
    }
}
```

### 示例 2：配置框架切换策略

```yaml
agent:
  ai:
    switch-strategy: fallback  # 推荐
    langchain4j:
      weight: 0.3
      enabled: true
    spring-ai:
      weight: 0.4
      enabled: true
      provider: openai
    spring-ai-alibaba:
      weight: 0.3
      enabled: true
```

**支持的切换策略：**
- `ALWAYS` - 始终使用默认框架
- `FALLBACK` - 失败时降级到备用框架
- `LOAD_BALANCE` - 按权重负载均衡
- `AGENT_SPECIFIC` - 根据 Agent 类型选择
- `TASK_AWARE` - 根据任务类型智能选择

---

## 📚 文档

| 文档 | 说明 |
|------|------|
| [AI Agent项目结构说明](01-docs/AI%20Agent项目结构说明.md) | 项目结构与架构设计 |
| [Spring-AI集成指南](01-docs/Spring-AI集成指南.md) | Spring AI 2.0 + Spring AI Alibaba 集成 |
| [混合AI架构集成指南](01-docs/混合AI架构集成指南.md) | 三框架混合架构详解 |
| [PostgreSQL向量数据库配置指南](01-docs/PostgreSQL向量数据库配置指南.md) | pgvector 配置与使用 |
| [垂直领域AI Agent商业设计](01-docs/垂直领域AI%20Agent商业设计.md) | 商业设计与场景分析 |

---

## 🔧 配置说明

### 数据库配置

每个 Agent 使用独立的 PostgreSQL 数据库：

```yaml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/openagent_{agent_id}
    username: postgres
    password: 123456
```

### Redis 配置

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
      password: ${REDIS_PASSWORD:}
```

### Kafka 配置

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: ${spring.application.name}-group
```

---

## 🛠️ 开发指南

### 创建新 Agent

使用 Python 脚本快速创建新 Agent：

```bash
python create_agent_modules.py your-new-agent
```

或手动创建：
1. 创建 Agent 目录结构
2. 配置 `pom.xml`
3. 创建 `Application.java`
4. 配置 `application.yml`
5. 创建数据库初始化脚本
6. 在父 `pom.xml` 中声明模块

### 添加依赖

```xml
<dependency>
    <groupId>com.qoobot</groupId>
    <artifactId>agent-core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

---

## 📊 依赖关系

```
openagent (父 POM)
  ├── agent-core (核心框架)
  │   ├── LangChain4J 集成
  │   ├── Spring AI 集成
  │   └── 混合架构服务
  └── 23 个业务 Agent (依赖 agent-core)
```

---

## 🔐 安全与合规

- ✅ 敏感信息通过环境变量管理
- ✅ API Key 不提交到代码仓库
- ✅ 支持 HTTPS 加密通信
- ✅ SQL 注入防护
- ✅ XSS 攻击防护

---

## 📈 监控与运维

### Actuator 端点

| 端点 | 说明 |
|------|------|
| `/actuator/health` | 健康检查 |
| `/actuator/metrics` | 性能指标 |
| `/actuator/info` | 应用信息 |
| `/actuator/prometheus` | Prometheus 指标 |

### 日志配置

```yaml
logging:
  level:
    com.qoobot.agent: DEBUG
    org.springframework.ai: DEBUG
    dev.langchain4j: DEBUG
```

---

## 🤝 贡献指南

欢迎参与本项目开发，贡献代码或提出建议！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

---

## 📝 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

---

## 📧 联系方式

- 项目主页: https://github.com/qoobot-com/openagent
- 问题反馈: https://github.com/qoobot-com/openagent/issues

---

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [LangChain4J](https://github.com/langchain4j/langchain4j)
- [Spring AI](https://github.com/spring-projects/spring-ai)
- [Spring AI Alibaba](https://github.com/alibaba/spring-ai-alibaba)
- [pgvector](https://github.com/pgvector/pgvector)

---

<div align="center">

**Made with ❤️ by Qoobot Team**

</div>