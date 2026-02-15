-- PostgreSQL 16 + pgvector 0.7.4 初始化脚本模板
-- 用于创建Agent系统的数据库和扩展

-- ============================================
-- 创建数据库
-- ============================================
DROP DATABASE IF EXISTS openagent_customer_service_agent;
CREATE DATABASE openagent_customer_service_agent
    ENCODING 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE template0;

\c openagent_customer_service_agent

-- ============================================
-- 启用必要的扩展
-- ============================================
-- 启用 pgvector 扩展用于向量相似度搜索
CREATE EXTENSION IF NOT EXISTS vector;

-- 启用 uuid-ossp 用于生成UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 启用 pg_trgm 用于模糊搜索
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- 启用 btree_gin 用于GIN索引
CREATE EXTENSION IF NOT EXISTS btree_gin;

-- ============================================
-- 创建schema
-- ============================================
CREATE SCHEMA IF NOT EXISTS agent_data;
CREATE SCHEMA IF NOT EXISTS agent_metadata;

-- ============================================
-- 创建向量存储表
-- ============================================
CREATE TABLE agent_data.embeddings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    agent_id VARCHAR(100) NOT NULL,
    embedding_id VARCHAR(255) NOT NULL,
    content TEXT,
    metadata JSONB,
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 创建向量相似度索引
CREATE INDEX idx_embeddings_agent_id ON agent_data.embeddings(agent_id);
CREATE INDEX idx_embeddings_embedding_id ON agent_data.embeddings(embedding_id);
CREATE INDEX idx_embeddings_vector ON agent_data.embeddings USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);
CREATE INDEX idx_embeddings_metadata ON agent_data.embeddings USING GIN (metadata);

-- ============================================
-- 创建Agent配置表
-- ============================================
CREATE TABLE agent_data.agent_config (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    agent_id VARCHAR(100) UNIQUE NOT NULL,
    agent_name VARCHAR(255) NOT NULL,
    agent_description TEXT,
    version VARCHAR(50) NOT NULL,
    config JSONB NOT NULL,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_agent_config_agent_id ON agent_data.agent_config(agent_id);
CREATE INDEX idx_agent_config_status ON agent_data.agent_config(status);

-- ============================================
-- 创建Agent执行日志表
-- ============================================
CREATE TABLE agent_data.execution_log (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    agent_id VARCHAR(100) NOT NULL,
    task_id VARCHAR(255) NOT NULL,
    task_type VARCHAR(100),
    input_text TEXT,
    output_text TEXT,
    execution_time_ms INTEGER,
    status VARCHAR(50),
    error_message TEXT,
    metadata JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 分区表 - 按月分区
CREATE TABLE agent_data.execution_log_partitioned (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    agent_id VARCHAR(100) NOT NULL,
    task_id VARCHAR(255) NOT NULL,
    task_type VARCHAR(100),
    input_text TEXT,
    output_text TEXT,
    execution_time_ms INTEGER,
    status VARCHAR(50),
    error_message TEXT,
    metadata JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
) PARTITION BY RANGE (created_at);

-- 创建分区函数
CREATE OR REPLACE FUNCTION create_monthly_partition(table_name TEXT, start_date DATE)
RETURNS VOID AS $$
DECLARE
    partition_name TEXT;
    end_date DATE;
BEGIN
    partition_name := table_name || '_p' || TO_CHAR(start_date, 'YYYY_MM');
    end_date := start_date + INTERVAL '1 month';

    EXECUTE format('CREATE TABLE IF NOT EXISTS %I PARTITION OF %I FOR VALUES FROM (%L) TO (%L)',
                   partition_name, table_name, start_date, end_date);
END;
$$ LANGUAGE plpgsql;

-- 创建索引
CREATE INDEX idx_execution_log_agent_id ON agent_data.execution_log(agent_id);
CREATE INDEX idx_execution_log_task_id ON agent_data.execution_log(task_id);
CREATE INDEX idx_execution_log_created_at ON agent_data.execution_log(created_at);
CREATE INDEX idx_execution_log_status ON agent_data.execution_log(status);
CREATE INDEX idx_execution_log_metadata ON agent_data.execution_log USING GIN (metadata);

-- ============================================
-- 创建知识库表
-- ============================================
CREATE TABLE agent_data.knowledge_base (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    agent_id VARCHAR(100) NOT NULL,
    document_id VARCHAR(255) NOT NULL,
    document_type VARCHAR(100),
    title TEXT,
    content TEXT,
    chunk_id INTEGER,
    total_chunks INTEGER,
    embedding vector(1536),
    metadata JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_knowledge_base_agent_id ON agent_data.knowledge_base(agent_id);
CREATE INDEX idx_knowledge_base_document_id ON agent_data.knowledge_base(document_id);
CREATE INDEX idx_knowledge_base_vector ON agent_data.knowledge_base USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);
CREATE INDEX idx_knowledge_base_metadata ON agent_data.knowledge_base USING GIN (metadata);

-- ============================================
-- 创建工具调用记录表
-- ============================================
CREATE TABLE agent_data.tool_call (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    agent_id VARCHAR(100) NOT NULL,
    task_id VARCHAR(255) NOT NULL,
    tool_name VARCHAR(255) NOT NULL,
    tool_type VARCHAR(100),
    parameters JSONB,
    result JSONB,
    execution_time_ms INTEGER,
    status VARCHAR(50),
    error_message TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_tool_call_agent_id ON agent_data.tool_call(agent_id);
CREATE INDEX idx_tool_call_task_id ON agent_data.tool_call(task_id);
CREATE INDEX idx_tool_call_tool_name ON agent_data.tool_call(tool_name);
CREATE INDEX idx_tool_call_created_at ON agent_data.tool_call(created_at);

-- ============================================
-- 创建性能指标表
-- ============================================
CREATE TABLE agent_data.performance_metrics (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    agent_id VARCHAR(100) NOT NULL,
    metric_name VARCHAR(255) NOT NULL,
    metric_value FLOAT8,
    metric_unit VARCHAR(50),
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    metadata JSONB
);

CREATE INDEX idx_performance_metrics_agent_id ON agent_data.performance_metrics(agent_id);
CREATE INDEX idx_performance_metrics_metric_name ON agent_data.performance_metrics(metric_name);
CREATE INDEX idx_performance_metrics_timestamp ON agent_data.performance_metrics(timestamp);

-- ============================================
-- 创建存储过程和函数
-- ============================================

-- 向量相似度搜索函数
CREATE OR REPLACE FUNCTION agent_data.search_similar_embeddings(
    p_agent_id VARCHAR(100),
    p_query_vector vector(1536),
    p_limit INTEGER DEFAULT 10,
    p_threshold FLOAT8 DEFAULT 0.7
)
RETURNS TABLE (
    id UUID,
    embedding_id VARCHAR(255),
    content TEXT,
    metadata JSONB,
    similarity FLOAT8
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        e.id,
        e.embedding_id,
        e.content,
        e.metadata,
        1 - (e.embedding <=> p_query_vector) AS similarity
    FROM agent_data.embeddings e
    WHERE e.agent_id = p_agent_id
      AND (1 - (e.embedding <=> p_query_vector)) >= p_threshold
    ORDER BY e.embedding <=> p_query_vector
    LIMIT p_limit;
END;
$$ LANGUAGE plpgsql;

-- 性能指标聚合函数
CREATE OR REPLACE FUNCTION agent_data.get_performance_stats(
    p_agent_id VARCHAR(100),
    p_metric_name VARCHAR(255),
    p_start_time TIMESTAMP WITH TIME ZONE,
    p_end_time TIMESTAMP WITH TIME ZONE
)
RETURNS TABLE (
    avg_value FLOAT8,
    min_value FLOAT8,
    max_value FLOAT8,
    count_value BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        AVG(pm.metric_value),
        MIN(pm.metric_value),
        MAX(pm.metric_value),
        COUNT(*)
    FROM agent_data.performance_metrics pm
    WHERE pm.agent_id = p_agent_id
      AND pm.metric_name = p_metric_name
      AND pm.timestamp BETWEEN p_start_time AND p_end_time;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- 创建视图
-- ============================================

-- Agent执行摘要视图
CREATE OR REPLACE VIEW agent_data.execution_summary AS
SELECT
    agent_id,
    task_type,
    COUNT(*) as total_tasks,
    AVG(execution_time_ms) as avg_execution_time,
    MIN(execution_time_ms) as min_execution_time,
    MAX(execution_time_ms) as max_execution_time,
    COUNT(*) FILTER (WHERE status = 'COMPLETED') as completed_tasks,
    COUNT(*) FILTER (WHERE status = 'FAILED') as failed_tasks,
    COUNT(*) FILTER (WHERE status = 'COMPLETED')::FLOAT / COUNT(*) * 100 as success_rate
FROM agent_data.execution_log
WHERE created_at >= NOW() - INTERVAL '24 hours'
GROUP BY agent_id, task_type;

-- ============================================
-- 插入默认数据
-- ============================================
INSERT INTO agent_data.agent_config (agent_id, agent_name, agent_description, version, config, status)
VALUES (
    'customer_service_agent',
    'Customer Service Agent',
    '智能客服AI Agent系统',
    '1.0.0',
    '{
        "model": {
            "provider": "openai",
            "modelName": "gpt-4",
            "maxTokens": 2000,
            "temperature": 0.7
        },
        "vector": {
            "dimension": 1536,
            "indexType": "ivfflat",
            "metricType": "cosine"
        },
        "performance": {
            "maxConcurrentRequests": 100,
            "threadPoolSize": 20,
            "cache": {
                "enabled": true,
                "maxSize": 1000
            }
        }
    }'::jsonb,
    'ACTIVE'
);

-- ============================================
-- 授予权限
-- ============================================
-- 根据实际用户名调整以下权限
GRANT ALL PRIVILEGES ON SCHEMA agent_data TO postgres;
GRANT ALL PRIVILEGES ON SCHEMA agent_metadata TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA agent_data TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA agent_metadata TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA agent_data TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA agent_metadata TO postgres;

-- ============================================
-- 验证安装
-- ============================================
SELECT
    'PostgreSQL version: ' || version() AS info
UNION ALL
SELECT
    'pgvector version: ' || extversion
FROM pg_extension
WHERE extname = 'vector';

-- 完成
SELECT 'Database initialization completed successfully!' AS status;
