# PostgreSQL 16 + pgvector 0.7.4 数据库配置指南

## 概述

本项目所有20个AI Agent系统统一使用 **PostgreSQL 16** + **pgvector 0.7.4** 作为主数据库和向量数据库。

### 技术栈

- **PostgreSQL**: 16.x
- **pgvector**: 0.7.4
- **PostgreSQL JDBC驱动**: 42.7.4
- **pgvector Java客户端**: 0.1.4-repackaged

## 快速开始

### 1. 启动数据库服务

使用Docker Compose启动所有依赖服务：

```bash
docker-compose up -d
```

这将启动以下服务：
- PostgreSQL 16 + pgvector (端口: 5432)
- Redis (端口: 6379)
- Kafka (端口: 9092)
- pgAdmin (端口: 5050)

### 2. 初始化数据库

为每个Agent系统执行初始化脚本：

```bash
# 初始化所有Agent数据库
psql -U postgres -h localhost -f scripts/db-init-drug-research-agent.sql
psql -U postgres -h localhost -f scripts/db-init-medical-diagnosis-agent.sql
# ... 其他Agent系统
```

或使用批量初始化脚本：

```bash
cd scripts
for file in db-init-*.sql; do
  echo "Initializing $file..."
  psql -U postgres -h localhost -f "$file"
done
```

### 3. 验证安装

连接到PostgreSQL并验证pgvector扩展：

```bash
psql -U postgres -h localhost -d openagent_drug_research_agent
```

```sql
-- 查看PostgreSQL版本
SELECT version();

-- 查看pgvector版本
SELECT extname, extversion FROM pg_extension WHERE extname = 'vector';

-- 测试向量相似度搜索
SELECT 1 - (array[1,2,3]::vector <=> array[1,2,3]::vector) AS similarity;
```

## 数据库结构

### 核心表

每个Agent系统包含以下核心表：

#### 1. embeddings (向量存储表)

存储文本嵌入向量，用于语义搜索和RAG：

```sql
CREATE TABLE agent_data.embeddings (
    id UUID PRIMARY KEY,
    agent_id VARCHAR(100) NOT NULL,
    embedding_id VARCHAR(255) NOT NULL,
    content TEXT,
    metadata JSONB,
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);
```

**索引**:
- `idx_embeddings_agent_id`: Agent ID索引
- `idx_embeddings_vector`: IVFFlat向量索引 (余弦相似度)
- `idx_embeddings_metadata`: GIN索引用于JSONB查询

#### 2. agent_config (Agent配置表)

存储Agent系统配置：

```sql
CREATE TABLE agent_data.agent_config (
    id UUID PRIMARY KEY,
    agent_id VARCHAR(100) UNIQUE NOT NULL,
    agent_name VARCHAR(255) NOT NULL,
    agent_description TEXT,
    version VARCHAR(50) NOT NULL,
    config JSONB NOT NULL,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);
```

#### 3. execution_log (执行日志表)

记录Agent任务执行日志：

```sql
CREATE TABLE agent_data.execution_log (
    id UUID PRIMARY KEY,
    agent_id VARCHAR(100) NOT NULL,
    task_id VARCHAR(255) NOT NULL,
    task_type VARCHAR(100),
    input_text TEXT,
    output_text TEXT,
    execution_time_ms INTEGER,
    status VARCHAR(50),
    error_message TEXT,
    metadata JSONB,
    created_at TIMESTAMP WITH TIME ZONE
);
```

**分区**: 按月分区以提升查询性能

#### 4. knowledge_base (知识库表)

存储RAG知识库文档：

```sql
CREATE TABLE agent_data.knowledge_base (
    id UUID PRIMARY KEY,
    agent_id VARCHAR(100) NOT NULL,
    document_id VARCHAR(255) NOT NULL,
    document_type VARCHAR(100),
    title TEXT,
    content TEXT,
    chunk_id INTEGER,
    total_chunks INTEGER,
    embedding vector(1536),
    metadata JSONB,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);
```

#### 5. tool_call (工具调用记录表)

记录工具调用历史：

```sql
CREATE TABLE agent_data.tool_call (
    id UUID PRIMARY KEY,
    agent_id VARCHAR(100) NOT NULL,
    task_id VARCHAR(255) NOT NULL,
    tool_name VARCHAR(255) NOT NULL,
    tool_type VARCHAR(100),
    parameters JSONB,
    result JSONB,
    execution_time_ms INTEGER,
    status VARCHAR(50),
    error_message TEXT,
    created_at TIMESTAMP WITH TIME ZONE
);
```

#### 6. performance_metrics (性能指标表)

存储性能监控数据：

```sql
CREATE TABLE agent_data.performance_metrics (
    id UUID PRIMARY KEY,
    agent_id VARCHAR(100) NOT NULL,
    metric_name VARCHAR(255) NOT NULL,
    metric_value FLOAT8,
    metric_unit VARCHAR(50),
    timestamp TIMESTAMP WITH TIME ZONE,
    metadata JSONB
);
```

## 向量相似度搜索

### 使用pgvector进行向量搜索

#### 1. 余弦相似度 (推荐)

```sql
SELECT
    id,
    embedding_id,
    content,
    1 - (embedding <=> '[0.1,0.2,...]'::vector) AS similarity
FROM agent_data.embeddings
WHERE agent_id = 'drug-research-agent'
ORDER BY embedding <=> '[0.1,0.2,...]'::vector
LIMIT 10;
```

#### 2. 欧几里得距离

```sql
SELECT
    id,
    embedding_id,
    content,
    - (embedding <-> '[0.1,0.2,...]'::vector) AS similarity
FROM agent_data.embeddings
WHERE agent_id = 'drug-research-agent'
ORDER BY embedding <-> '[0.1,0.2,...]'::vector
LIMIT 10;
```

#### 3. 内积

```sql
SELECT
    id,
    embedding_id,
    content,
    (embedding <#> '[0.1,0.2,...]'::vector) AS similarity
FROM agent_data.embeddings
WHERE agent_id = 'drug-research-agent'
ORDER BY embedding <#> '[0.1,0.2,...]'::vector
LIMIT 10;
```

### 使用存储函数

```sql
-- 使用预定义的搜索函数
SELECT * FROM agent_data.search_similar_embeddings(
    'drug-research-agent',
    '[0.1,0.2,...]'::vector,
    10,  -- limit
    0.7  -- threshold
);
```

## Java集成

### VectorDatabase工具类

```java
@Autowired
private VectorDatabase vectorDatabase;

// 插入向量
vectorDatabase.insertEmbedding(
    "drug-research-agent",
    "doc-001",
    "这是文档内容",
    Map.of("category", "research"),
    embeddingArray
);

// 相似度搜索
List<VectorSearchResult> results = vectorDatabase.searchSimilar(
    "drug-research-agent",
    queryEmbedding,
    10,    // limit
    0.7    // threshold
);

// 批量插入
List<EmbeddingData> embeddings = ...;
vectorDatabase.batchInsertEmbeddings("drug-research-agent", embeddings);
```

## 性能优化

### 1. 向量索引优化

```sql
-- 创建IVFFlat索引（适合大规模数据）
CREATE INDEX idx_embeddings_vector_ivfflat
ON agent_data.embeddings
USING ivfflat (embedding vector_cosine_ops)
WITH (lists = 100);

-- 重新索引
REINDEX INDEX idx_embeddings_vector_ivfflat;

-- 删除并重建索引
DROP INDEX IF EXISTS idx_embeddings_vector_ivfflat;
CREATE INDEX idx_embeddings_vector_ivfflat
ON agent_data.embeddings
USING ivfflat (embedding vector_cosine_ops)
WITH (lists = 100);
```

### 2. 查询优化

```sql
-- 使用EXPLAIN ANALYZE分析查询
EXPLAIN ANALYZE
SELECT * FROM agent_data.search_similar_embeddings(...);

-- 使用LIMIT限制结果数量
-- 使用WHERE条件过滤
-- 使用合适的相似度阈值
```

### 3. 分区表优化

```sql
-- 创建月度分区
SELECT agent_data.create_monthly_partition(
    'agent_data.execution_log_partitioned',
    '2024-02-01'::date
);

-- 查看分区信息
SELECT
    schemaname,
    tablename,
    partitionname,
    partitionbound
FROM pg_partitions
WHERE tablename = 'execution_log_partitioned';
```

## 监控和维护

### 1. 表大小监控

```sql
-- 查看表大小
SELECT
    schemaname,
    tablename,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS size
FROM pg_tables
WHERE schemaname = 'agent_data'
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;
```

### 2. 索引使用情况

```sql
-- 查看索引使用统计
SELECT
    schemaname,
    tablename,
    indexname,
    idx_scan,
    idx_tup_read,
    idx_tup_fetch
FROM pg_stat_user_indexes
WHERE schemaname = 'agent_data'
ORDER BY idx_scan DESC;
```

### 3. 连接数监控

```sql
-- 查看当前连接
SELECT
    usename,
    application_name,
    client_addr,
    state,
    query_start,
    state_change,
    query
FROM pg_stat_activity
WHERE datname LIKE 'openagent_%';
```

### 4. 定期维护

```sql
-- 分析表以更新统计信息
ANALYZE agent_data.embeddings;
ANALYZE agent_data.knowledge_base;

-- 清理死元组
VACUUM ANALYZE agent_data.embeddings;

-- 完全清理（锁表）
VACUUM FULL ANALYZE agent_data.embeddings;
```

## 备份和恢复

### 备份

```bash
# 备份单个数据库
pg_dump -U postgres -h localhost -d openagent_drug_research_agent \
  -f backup/drug_research_agent_$(date +%Y%m%d).sql

# 备份所有Agent数据库
for db in $(psql -U postgres -h localhost -lqt | cut -d \| -f 1 | grep openagent_); do
  pg_dump -U postgres -h localhost -d $db -f backup/${db}_$(date +%Y%m%d).sql
done
```

### 恢复

```bash
# 恢复数据库
psql -U postgres -h localhost -d openagent_drug_research_agent \
  -f backup/drug_research_agent_20240215.sql
```

## 故障排除

### 常见问题

#### 1. pgvector扩展未安装

```bash
# 在Docker容器内
docker exec -it openagent-postgres bash
psql -U postgres -d openagent

CREATE EXTENSION IF NOT EXISTS vector;
```

#### 2. 向量索引创建失败

```sql
-- 检查是否有足够的数据
SELECT COUNT(*) FROM agent_data.embeddings;

-- IVFFlat需要至少1000条数据
-- 如果数据量小，使用精确搜索
DROP INDEX IF EXISTS idx_embeddings_vector_ivfflat;
```

#### 3. 连接超时

```yaml
# application.yml
spring:
  datasource:
    hikari:
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

## 配置参考

### application.yml完整配置

```yaml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/openagent_drug_research_agent
    username: postgres
    password: 123456
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000

agent:
  vector:
    enabled: true
    dimension: 1536
    index-type: ivfflat
    metric-type: cosine
    index-lists: 100
```

## 参考资源

- [PostgreSQL官方文档](https://www.postgresql.org/docs/16/)
- [pgvector GitHub](https://github.com/pgvector/pgvector)
- [pgvector Java客户端](https://github.com/pgvector/pgvector-java)
- [LangChain4J文档](https://docs.langchain4j.dev/)

## License

MIT License
