# Spring AI 2.0 + Spring AI Alibaba 集成指南

## 概述

本项目已集成 **Spring AI 2.0** (核心框架) 和 **Spring AI Alibaba** (国内落地方案)，为 20 个垂直领域 Agent 提供统一的 AI 能力。

### 技术栈

- **Spring AI**: 1.0.0-M5
- **Spring AI Alibaba**: 1.0.0-M5.2
- **Spring Boot**: 3.5.10
- **Java**: 21

## 架构设计

### 双模型支持

```
┌─────────────────────────────────────────────────────────┐
│                  Agent Core (Spring AI)                 │
├─────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │
│  │ AIChatService│ │AIEmbedding   │ │ SpringAIRAGService│ │
│  │             │ │Service       │ │                 │  │
│  │ 统一聊天接口  │ │向量嵌入      │ │ RAG检索生成     │  │
│  └─────────────┘  └─────────────┘  └─────────────────┘  │
├─────────────────────────────────────────────────────────┤
│         模型适配层 (可切换)                                │
├─────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐                       │
│  │ Spring AI   │  │ Spring AI   │                       │
│  │ OpenAI      │  │ Alibaba     │                       │
│  │ (海外)      │  │ (国内)      │                       │
│  └─────────────┘  └─────────────┘                       │
├─────────────────────────────────────────────────────────┤
│         向量存储层                                         │
├─────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐                       │
│  │ PostgreSQL   │  │ Milvus      │                       │
│  │ + pgvector   │  │ (可选)      │                       │
│  └─────────────┘  └─────────────┘                       │
└─────────────────────────────────────────────────────────┘
```

## 核心组件

### 1. AIChatService

**功能**：统一的 AI 聊天服务

**使用示例**：

```java
@Autowired
private AIChatService chatService;

// 简单聊天
String response = chatService.chat("你好");

// 带系统提示的聊天
String response = chatService.chatWithSystem(
    "你是一个专业的医疗助手",
    "患者出现了头痛症状，该怎么办？"
);

// RAG 聊天
String response = chatService.chatWithRAG(
    "这个化合物有什么副作用？",
    contextDocuments
);

// 流式聊天
chatService.chatStream("介绍一下...", content -> {
    System.out.println(content); // 实时接收内容
});

// 文本总结
String summary = chatService.summarize(longText, 500);
```

### 2. AIEmbeddingService

**功能**：向量嵌入和相似度搜索

**使用示例**：

```java
@Autowired
private AIEmbeddingService embeddingService;

// 生成向量
float[] embedding = embeddingService.embed("这是一段文本");

// 批量生成向量
List<float[]> embeddings = embeddingService.embedBatch(texts);

// 添加文档到向量库
embeddingService.addDocument(
    "doc-001",
    "文档内容",
    Map.of("agent_id", "drug-research-agent", "type", "research")
);

// 相似度搜索
List<Document> results = embeddingService.similaritySearch("查询内容", 5);

// 按元数据过滤搜索
List<Document> results = embeddingService.similaritySearchWithFilter(
    "查询内容",
    5,
    Map.of("agent_id", "drug-research-agent")
);

// 从文件加载文档
embeddingService.loadAndAddDocument(
    resource,
    "drug-research-agent",
    "research_paper"
);
```

### 3. SpringAIRAGService

**功能**：检索增强生成（RAG）

**使用示例**：

```java
@Autowired
private SpringAIRAGService ragService;

// 标准 RAG
String answer = ragService.retrieveAndGenerate(
    "这个药物有什么禁忌症？",
    "drug-research-agent"
);

// 带 Rerank 的 RAG
String answer = ragService.retrieveAndGenerateWithRerank(
    "药物的副作用",
    "drug-research-agent",
    10
);

// Agent 任务 RAG
String result = ragService.executeAgentTaskWithRAG(
    "drug-research-agent",
    "化合物筛选",
    "筛选具有抗肿瘤活性的化合物",
    "search-compound, analyze-structure"
);

// 文档索引
ragService.indexDocument(
    "drug-research-agent",
    "clinical_trial",
    "临床试验内容...",
    Map.of("trial_id", "TRIAL-001", "phase", "III")
);

// 批量索引
List<DocumentData> documents = ...;
ragService.batchIndexDocuments(documents);
```

## 配置说明

### application.yml 配置

```yaml
spring:
  ai:
    # OpenAI 配置
    openai:
      api-key: ${OPENAI_API_KEY:your-key}
      chat:
        model: gpt-4o-mini
        temperature: 0.7
        max-tokens: 2000
      enabled: true

    # 阿里云配置
    dashscope:
      api-key: ${DASHSCOPE_API_KEY:your-key}
      chat:
        model: qwen-plus
      enabled: false

    # 向量存储
    vectorstore:
      pgvector:
        host: localhost
        port: 5432
        database: openagent_drug_research_agent
        username: postgres
        password: 123456
        dimension: 1536
        index-type: ivfflat

    # RAG 配置
    rag:
      enabled: true
      top-k: 5
      threshold: 0.7
      chunk-size: 1000
      chunk-overlap: 200
```

### 环境变量配置

```bash
# OpenAI 配置
export OPENAI_API_KEY=sk-...
export OPENAI_BASE_URL=https://api.openai.com/v1

# 阿里云配置
export DASHSCOPE_API_KEY=sk-...

# PostgreSQL 配置
export PGHOST=localhost
export PGPORT=5432
export PGDATABASE=openagent_drug_research_agent
export PGUSER=postgres
export PGPASSWORD=123456
```

## 模型切换策略

### 海外部署（OpenAI）

```yaml
spring:
  ai:
    openai:
      enabled: true
    dashscope:
      enabled: false
```

### 国内部署（DashScope）

```yaml
spring:
  ai:
    openai:
      enabled: false
    dashscope:
      enabled: true
      api-key: ${DASHSCOPE_API_KEY}
```

### 双模型容错

```java
@Service
public class HybridModelService {

    @Autowired
    private ChatClient.Builder openAiClient;

    @Autowired
    @Qualifier("dashscopeChatClient")
    private ChatClient.Builder dashscopeClient;

    public String chatWithFallback(String message) {
        try {
            // 优先使用 OpenAI
            return openAiClient.build().prompt()
                .user(message)
                .call()
                .content();
        } catch (Exception e) {
            log.warn("OpenAI failed, fallback to DashScope", e);
            // 降级到阿里云
            return dashscopeClient.build().prompt()
                .user(message)
                .call()
                .content();
        }
    }
}
```

## 数据库初始化

### 创建 Spring AI 向量表

```sql
-- 在每个 Agent 数据库中执行
CREATE TABLE IF NOT EXISTS spring_ai_vector_store (
    id SERIAL PRIMARY KEY,
    content TEXT,
    metadata JSONB,
    vector vector(1536)
);

-- 创建索引
CREATE INDEX idx_vectorstore_vector
ON spring_ai_vector_store
USING ivfflat (vector vector_cosine_ops)
WITH (lists = 100);

-- 创建元数据索引
CREATE INDEX idx_vectorstore_metadata
ON spring_ai_vector_store USING gin (metadata);
```

## 在 Agent 中使用

### 步骤 1：添加依赖

```xml
<dependency>
    <groupId>com.qoobot</groupId>
    <artifactId>agent-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 步骤 2：配置 application.yml

```yaml
spring:
  application:
    name: drug-research-agent
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
    vectorstore:
      pgvector:
        database: openagent_drug_research_agent

agent:
  id: drug-research-agent
  name: 药物研发智能体
```

### 步骤 3：编写 Agent 服务

```java
@Service
public class DrugResearchAgentService {

    @Autowired
    private AIChatService chatService;

    @Autowired
    private SpringAIRAGService ragService;

    public String analyzeCompound(String compoundName) {
        // 使用 RAG 检索相关文献
        return ragService.retrieveAndGenerate(
            "分析化合物 " + compoundName + " 的性质",
            "drug-research-agent"
        );
    }

    public String screenCompounds(String criteria) {
        // Agent 任务执行
        return ragService.executeAgentTaskWithRAG(
            "drug-research-agent",
            "compound_screening",
            "筛选符合条件的化合物：" + criteria,
            "search-database, analyze-structure, predict-activity"
        );
    }
}
```

## 高级特性

### 1. 自定义 Prompt 模板

```java
@Autowired
private AIChatService chatService;

public String customPrompt(String userQuery, String context) {
    String template = """
        你是 {role} 专家。
        基于以下信息回答问题：

        {context}

        问题：{question}
        """;

    Map<String, Object> variables = Map.of(
        "role", "医疗",
        "context", context,
        "question", userQuery
    );

    return chatService.chatWithTemplate(template, variables);
}
```

### 2. 流式响应

```java
@GetMapping("/stream")
public Flux<String> chatStream(@RequestParam String message) {
    return Flux.create(sink -> {
        chatService.chatStream(message,
            content -> sink.next(content),
            error -> sink.error(error),
            () -> sink.complete()
        );
    });
}
```

### 3. Function Calling

```java
@Bean
public FunctionCallback weatherFunction() {
    return FunctionCallback.builder()
        .function("getWeather", (GetWeatherRequest request) -> {
            // 调用天气 API
            return weatherService.getWeather(request.getLocation());
        })
        .inputType(GetWeatherRequest.class)
        .description("获取指定地点的天气信息")
        .build();
}

// 使用 Function Calling
String response = ChatClient.builder(chatModel)
    .defaultFunctions("getWeather")
    .build()
    .prompt()
    .user("北京今天天气怎么样？")
    .call()
    .content();
```

### 4. Multi-Agent 协作

```java
@Service
public class MultiAgentOrchestrator {

    @Autowired
    private AIChatService chatService;

    @Autowired
    private SpringAIRAGService ragService;

    public String orchestrateTask(String task) {
        // 1. 任务分解
        String plan = chatService.chat("""
            分析任务并分解为子任务：
            %s

            返回 JSON 格式的任务列表，每个任务包含：
            - agent_type: 执行的 Agent 类型
            - task_description: 任务描述
            - dependencies: 依赖的任务
            """.formatted(task));

        // 2. 任务调度与执行
        // ... 实现任务编排逻辑

        // 3. 结果汇总
        return summary;
    }
}
```

## 性能优化

### 1. 向量索引优化

```sql
-- 数据量达到 1000+ 后创建 IVFFlat 索引
CREATE INDEX CONCURRENTLY idx_vectorstore_ivfflat
ON spring_ai_vector_store
USING ivfflat (vector vector_cosine_ops)
WITH (lists = sqrt(document_count));

-- 定期重新索引
REINDEX INDEX idx_vectorstore_ivfflat;
```

### 2. 批量操作

```java
// 批量嵌入
List<String> texts = ...;
List<float[]> embeddings = embeddingService.embedBatch(texts);

// 批量添加文档
List<Document> documents = texts.stream()
    .map(text -> new Document(UUID.randomUUID().toString(), text))
    .toList();
embeddingService.addDocuments(documents);
```

### 3. 缓存策略

```java
@Service
@CacheConfig(cacheNames = "ai-chat")
public class CachedChatService {

    @Autowired
    private AIChatService chatService;

    @Cacheable(key = "#message.hashCode()")
    public String chat(String message) {
        return chatService.chat(message);
    }
}
```

## 监控与日志

### 日志配置

```yaml
logging:
  level:
    org.springframework.ai: DEBUG
    org.springframework.ai.chat: DEBUG
    org.springframework.ai.vectorstore: DEBUG
```

### Metrics 指标

```java
@Component
public class AIMetrics {

    private final MeterRegistry meterRegistry;
    private final Counter chatCounter;
    private final Timer chatTimer;

    public AIMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.chatCounter = Counter.builder("ai.chat.requests")
            .description("AI chat requests")
            .register(meterRegistry);
        this.chatTimer = Timer.builder("ai.chat.duration")
            .description("AI chat duration")
            .register(meterRegistry);
    }

    public void recordChat(String model, Duration duration) {
        chatCounter.increment(Tags.of("model", model));
        chatTimer.record(duration);
    }
}
```

## 故障排除

### 问题 1：向量表未初始化

```bash
# 检查表是否存在
psql -U postgres -d openagent_drug_research_agent -c "\dt"

# 手动初始化
psql -U postgres -d openagent_drug_research_agent -f scripts/init-spring-ai-tables.sql
```

### 问题 2：API Key 配置错误

```bash
# 验证环境变量
echo $OPENAI_API_KEY

# 测试连接
curl https://api.openai.com/v1/models \
  -H "Authorization: Bearer $OPENAI_API_KEY"
```

### 问题 3：向量搜索性能慢

```sql
-- 分析查询
EXPLAIN ANALYZE
SELECT * FROM spring_ai_vector_store
ORDER BY vector <=> '[0.1,0.2,...]'::vector
LIMIT 5;

-- 重新构建索引
DROP INDEX idx_vectorstore_vector;
CREATE INDEX idx_vectorstore_vector
ON spring_ai_vector_store
USING ivfflat (vector vector_cosine_ops)
WITH (lists = 100);
```

## 参考资源

- [Spring AI 官方文档](https://docs.spring.io/spring-ai/reference/)
- [Spring AI Alibaba 文档](https://sca.aliyun.com/ai/)
- [LangChain4J 文档](https://docs.langchain4j.dev/)
- [pgvector GitHub](https://github.com/pgvector/pgvector)

## License

MIT License
