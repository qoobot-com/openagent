# AI Agent项目结构说明

## 项目概览

本项目是基于Spring Boot 3.5.10 + LangChain4J构建的垂直领域AI Agent系统集合,包含20个完整的AI Agent应用系统。

## 项目结构

```
openagent/
├── agent-systems/              # AI Agent系统集合
│   ├── agent-core/            # Agent核心框架与通用组件
│   │   └── pom.xml
│   ├── drug-research-agent/   # 1. 药物研发协同Agent
│   │   ├── pom.xml
│   │   ├── src/main/java/com/qoobot/agent/drug-research-agent/
│   │   │   ├── Application.java                    # 应用启动类
│   │   │   ├── config/                             # 配置类
│   │   │   ├── controller/                         # REST API控制器
│   │   │   ├── service/                            # 业务服务层
│   │   │   ├── repository/                         # 数据访问层
│   │   │   ├── entity/                             # 实体类
│   │   │   ├── mapper/                             # MyBatis Mapper
│   │   │   └── module/                             # Agent子模块
│   │   │       ├── literature-analysis/             # 文献分析Agent
│   │   │       ├── experiment-simulation/           # 实验模拟Agent
│   │   │       └── compliance-audit/               # 合规审计Agent
│   │   └── src/main/resources/
│   │       ├── application.yml                      # 应用配置
│   │       └── mapper/                             # MyBatis XML映射
│   │
│   ├── medical-diagnosis-agent/  # 2. 智能医疗诊断Agent
│   │   ├── module/
│   │   │   ├── medical-record-analysis/             # 病历分析Agent
│   │   │   ├── multidisciplinary-collaboration/     # 多学科协作Agent
│   │   │   └── treatment-optimization/             # 治疗方案优化Agent
│   │
│   ├── medical-device-agent/    # 3. 医疗设备协同Agent
│   │   ├── module/
│   │   │   ├── device-monitoring/                   # 设备状态监测Agent
│   │   │   ├── multimodal-diagnosis/               # 多模态诊断Agent
│   │   │   └── maintenance-decision/               # 维护决策Agent
│   │
│   ├── agricultural-pest-agent/ # 4. 农业病虫害预测Agent
│   │   ├── module/
│   │   │   ├── environment-perception/              # 环境感知Agent
│   │   │   ├── pest-prediction/                    # 病虫害预测Agent
│   │   │   └── precision-control/                  # 精准防控Agent
│   │
│   ├── cross-border-logistics-agent/ # 5. 跨境物流优化Agent
│   │   ├── module/
│   │   │   ├── dynamic-route-planning/             # 动态路径规划Agent
│   │   │   ├── exception-handling/                 # 异常处理Agent
│   │   │   └── cost-optimization/                  # 成本优化Agent
│   │
│   ├── nuclear-maintenance-agent/ # 6. 核电设备预测性维护Agent
│   │   ├── module/
│   │   │   ├── equipment-monitoring/                # 设备状态监测Agent
│   │   │   ├── fault-prediction/                   # 故障预测Agent
│   │   │   └── maintenance-decision/               # 维修决策Agent
│   │
│   ├── power-grid-agent/       # 7. 电网负荷预测Agent
│   │   ├── module/
│   │   │   ├── load-perception/                    # 负荷感知Agent
│   │   │   ├── multimodal-prediction/              # 多模态预测Agent
│   │   │   └── dispatch-decision/                  # 调度决策Agent
│   │
│   ├── semiconductor-optimization-agent/ # 8. 半导体工艺优化Agent
│   │   ├── module/
│   │   │   ├── process-perception/                 # 工艺参数感知Agent
│   │   │   ├── multi-objective-optimization/       # 多目标优化Agent
│   │   │   └── adaptive-control/                   # 自适应控制Agent
│   │
│   ├── medical-device-agent/    # 9. 医疗设备协同Agent
│   │   ├── module/
│   │   │   ├── device-monitoring/
│   │   │   ├── multimodal-diagnosis/
│   │   │   └── maintenance-decision/
│   │
│   ├── financial-pricing-agent/ # 10. 金融动态定价Agent
│   │   ├── module/
│   │   │   ├── market-perception/                  # 市场感知Agent
│   │   │   ├── arbitrage-prediction/               # 套利机会预测Agent
│   │   │   └── autonomous-execution/               # 自主执行Agent
│   │
│   ├── warehouse-scheduling-agent/ # 11. 智能仓储调度Agent
│   │   ├── module/
│   │   │   ├── order-analysis/                     # 订单分析Agent
│   │   │   ├── path-planning/                     # 路径规划Agent
│   │   │   └── dynamic-scheduling/                 # 动态调度Agent
│   │
│   ├── construction-safety-agent/ # 12. 建筑安全巡检Agent
│   │   ├── module/
│   │   │   ├── environment-perception/
│   │   │   ├── risk-prediction/
│   │   │   └── automatic-disposal/
│   │
│   ├── supply-chain-agent/    # 13. 供应链韧性Agent
│   │   ├── module/
│   │   │   ├── risk-perception/                    # 风险感知Agent
│   │   │   ├── scenario-simulation/                # 多方案推演Agent
│   │   │   └── supplier-collaboration/             # 供应商协同Agent
│   │
│   ├── customer-service-agent/ # 14. 智能客服Agent
│   │   ├── module/
│   │   │   ├── customer-need-analysis/             # 客户需求分析Agent
│   │   │   ├── cross-system-query/                 # 跨系统查询Agent
│   │   │   └── solution-generation/                # 解决方案生成Agent
│   │
│   ├── industrial-quality-agent/ # 15. 工业质检Agent
│   │   ├── module/
│   │   │   ├── defect-detection/                   # 缺陷检测Agent
│   │   │   ├── quality-standard-management/         # 质检标准管理Agent
│   │   │   └── quality-analysis/                   # 质量分析Agent
│   │
│   ├── intelligent-traffic-agent/ # 16. 智能交通管理Agent
│   │   ├── module/
│   │   │   ├── traffic-perception/                 # 交通状态感知Agent
│   │   │   ├── signal-optimization/                # 信号优化Agent
│   │   │   └── emergency-disposal/                 # 应急处置Agent
│   │
│   ├── marketing-planning-agent/ # 17. 智能营销策划Agent
│   │   ├── module/
│   │   │   ├── market-insight/                     # 市场洞察Agent
│   │   │   ├── planning-generation/                # 策划生成Agent
│   │   │   └── effect-prediction/                 # 效果预测Agent
│   │
│   ├── intelligent-ops-agent/ # 18. 智能运维管理Agent
│   │   ├── module/
│   │   │   ├── fault-perception/                   # 故障感知Agent
│   │   │   ├── cause-analysis/                    # 原因分析Agent
│   │   │   └── auto-repair/                       # 自动修复Agent
│   │
│   ├── investment-advisor-agent/ # 19. 智能投资顾问Agent
│   │   ├── module/
│   │   │   ├── customer-profiling/                 # 客户画像Agent
│   │   │   ├── asset-allocation/                   # 资产配置Agent
│   │   │   └── dynamic-tuning/                    # 动态调优Agent
│   │
│   └── power-trading-agent/    # 20. 智能电力交易Agent
│       ├── module/
│       │   ├── market-perception/
│       │   ├── trading-strategy/
│       │   └── compliance-monitoring/
│
├── docs/                         # 设计文档
│   ├── 垂直领域AI Agent商业设计.md           # 总体设计方案
│   ├── AI Agent子系统设计方案-总索引.md       # 子模块索引
│   └── [各系统详细设计文档]
│
└── pom.xml                       # 父POM配置
```

## 技术栈

### 核心框架
- **Spring Boot**: 3.5.10
- **Java**: 21
- **Maven**: 3.9.0+

### AI/LLM
- **LangChain4J**: 0.34.0
- **OpenAI API**: GPT-4

### 数据存储
- **PostgreSQL**: 主数据库
- **MySQL**: 备选数据库
- **Redis**: 缓存
- **MongoDB**: 文档存储
- **Neo4j**: 知识图谱
- **InfluxDB**: 时序数据库
- **Milvus**: 向量数据库

### 消息与队列
- **Apache Kafka**: 消息队列
- **RabbitMQ**: 任务队列

### 数据访问
- **MyBatis Plus**: 3.5.7
- **Spring Data**: Redis/Mongo/Neo4j

### 监控与可观测性
- **Spring Boot Actuator**: 监控端点
- **Micrometer**: 指标收集
- **Prometheus**: 指标存储
- **Zipkin**: 链路追踪

### API文档
- **SpringDoc OpenAPI**: 2.3.0

## Agent核心框架 (agent-core)

### 核心接口

#### Agent接口
所有Agent系统的基础接口,定义了Agent的核心能力:
```java
public interface Agent {
    String process(String userRequest);
    String processWithContext(String userRequest, Map<String, Object> context);
    String chat(String messages);
    AgentConfig getConfig();
    AgentHealth checkHealth();
}
```

#### 配置类
- `AgentConfig`: Agent配置
- `ModelConfig`: 模型配置
- `ToolConfig`: 工具配置
- `ApiConfig`: API配置
- `SecurityConfig`: 安全配置
- `PerformanceConfig`: 性能配置

#### 健康检查
- `AgentHealth`: Agent健康状态

## Agent子模块规范

每个Agent系统包含3个核心子模块:

### 1. 感知型模块 (PERCEPTION)
- 负责数据采集与监控
- 实时感知环境状态
- 数据预处理与质量检查

### 2. 分析型模块 (ANALYSIS)
- 负责数据分析与推理
- 使用AI模型进行预测
- 生成分析报告

### 3. 决策/执行型模块 (DECISION/EXECUTION)
- 负责决策制定与任务执行
- 优化策略生成
- 自动执行与反馈

## MCP协议集成

所有Agent系统通过MCP(Model Context Protocol)协议进行工具调用与系统间通信:

### MCP工具注册
```yaml
agent:
  tools:
    mcp:
      enabled: true
      endpoint: http://localhost:8080/mcp
      tools:
        - tool-id: database-query
          description: 查询数据库
        - tool-id: external-api
          description: 调用外部API
```

## 开发指南

### 创建新的Agent模块

1. 在对应的Agent系统下创建module目录
2. 创建Agent接口类(继承Agent接口)
3. 创建Agent实现类(使用@SystemMessage注解定义行为)
4. 实现具体的业务逻辑
5. 添加相应的Controller、Service、Repository等

### 示例代码

```java
@Service
@SystemMessage("""
    你是XXX Agent,负责XXX相关任务。
    你需要:
    1. 理解用户需求
    2. 调用相关工具
    3. 提供清晰结果
    """)
public class XXXAgentImpl implements XXXAgent {
    @Override
    public String process(String userRequest) {
        // 实现具体逻辑
    }
}
```

## 构建与运行

### 构建整个项目
```bash
mvn clean install
```

### 构建特定Agent系统
```bash
cd agent-systems/drug-research-agent
mvn clean install
```

### 运行Agent系统
```bash
mvn spring-boot:run
```

### 访问API
- Swagger UI: http://localhost:8080/swagger-ui.html
- Actuator: http://localhost:8080/actuator/health

## 配置说明

### 数据库配置
每个Agent系统使用独立的PostgreSQL数据库:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/openagent_{agent_id}
```

### Redis配置
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
```

### Kafka配置
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: ${spring.application.name}-group
```

## 依赖关系

```
openagent (父POM)
  └── agent-systems (父POM)
        ├── agent-core (核心框架)
        └── [20个Agent子系统] (依赖agent-core)
```

## 下一步计划

1. **完善agent-core**: 添加更多通用组件与工具类
2. **实现MCP协议**: 完成MCP工具注册与调用框架
3. **数据库初始化**: 创建各Agent系统的数据库表结构
4. **API文档**: 完善各Agent的API接口文档
5. **单元测试**: 编写核心组件的单元测试
6. **示例实现**: 完成1-2个Agent的完整实现作为示例

## 贡献指南

欢迎参与本项目开发,贡献代码或提出建议。

## 许可证

MIT License
