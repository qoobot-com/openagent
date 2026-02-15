-- 零售电商智能体数据库初始化脚本
-- Database: openagent_retail_agent

\c postgres;

-- 创建数据库
DROP DATABASE IF EXISTS openagent_retail_agent;
CREATE DATABASE openagent_retail_agent ENCODING 'UTF8';

\c openagent_retail_agent;

-- 创建 agent_data schema
CREATE SCHEMA IF NOT EXISTS agent_data;

-- 创建向量扩展
CREATE EXTENSION IF NOT EXISTS vector;

-- 1. embeddings (向量存储表)
CREATE TABLE agent_data.embeddings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    agent_id VARCHAR(100) NOT NULL,
    embedding_id VARCHAR(255) NOT NULL,
    content TEXT,
    metadata JSONB,
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_embeddings_agent_id ON agent_data.embeddings(agent_id);
CREATE INDEX idx_embeddings_vector ON agent_data.embeddings USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);
CREATE INDEX idx_embeddings_metadata ON agent_data.embeddings USING gin (metadata);

-- 2. agent_config (Agent配置表)
CREATE TABLE agent_data.agent_config (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    agent_id VARCHAR(100) UNIQUE NOT NULL,
    agent_name VARCHAR(255) NOT NULL,
    agent_description TEXT,
    version VARCHAR(50) NOT NULL,
    config JSONB NOT NULL,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 3. execution_log (执行日志表)
CREATE TABLE agent_data.execution_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
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

CREATE INDEX idx_execution_log_agent_id ON agent_data.execution_log(agent_id);
CREATE INDEX idx_execution_log_task_id ON agent_data.execution_log(task_id);
CREATE INDEX idx_execution_log_created_at ON agent_data.execution_log(created_at);

-- 4. knowledge_base (知识库表)
CREATE TABLE agent_data.knowledge_base (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
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

-- 5. tool_call (工具调用记录表)
CREATE TABLE agent_data.tool_call (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
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

-- 6. performance_metrics (性能指标表)
CREATE TABLE agent_data.performance_metrics (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    agent_id VARCHAR(100) NOT NULL,
    metric_name VARCHAR(255) NOT NULL,
    metric_value FLOAT8,
    metric_unit VARCHAR(50),
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    metadata JSONB
);

CREATE INDEX idx_performance_metrics_agent_id ON agent_data.performance_metrics(agent_id);
CREATE INDEX idx_performance_metrics_timestamp ON agent_data.performance_metrics(timestamp);

-- 零售专用表: products (商品表)
CREATE TABLE agent_data.products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id VARCHAR(255) UNIQUE NOT NULL,
    product_name VARCHAR(500) NOT NULL,
    category VARCHAR(100),
    brand VARCHAR(255),
    price DECIMAL(10,2),
    stock_quantity INTEGER,
    description TEXT,
    features TEXT[],
    tags TEXT[],
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_products_product_id ON agent_data.products(product_id);
CREATE INDEX idx_products_category ON agent_data.products(category);
CREATE INDEX idx_products_brand ON agent_data.products(brand);
CREATE INDEX idx_products_tags ON agent_data.products USING gin (tags);
CREATE INDEX idx_products_vector ON agent_data.products USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- 零售专用表: customers (客户表)
CREATE TABLE agent_data.customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    gender VARCHAR(20),
    age INTEGER,
    location VARCHAR(255),
    customer_level VARCHAR(50),
    preferences TEXT[],
    purchase_history JSONB,
    behavior_tags TEXT[],
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_customers_customer_id ON agent_data.customers(customer_id);
CREATE INDEX idx_customers_customer_level ON agent_data.customers(customer_level);
CREATE INDEX idx_customers_behavior_tags ON agent_data.customers USING gin (behavior_tags);
CREATE INDEX idx_customers_vector ON agent_data.customers USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- 零售专用表: recommendations (推荐记录表)
CREATE TABLE agent_data.recommendations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    recommendation_id VARCHAR(255) UNIQUE NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    recommendation_type VARCHAR(100),
    score FLOAT8,
    reason TEXT,
    context JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_recommendations_recommendation_id ON agent_data.recommendations(recommendation_id);
CREATE INDEX idx_recommendations_customer_id ON agent_data.recommendations(customer_id);
CREATE INDEX idx_recommendations_product_id ON agent_data.recommendations(product_id);
CREATE INDEX idx_recommendations_created_at ON agent_data.recommendations(created_at);

-- 零售专用表: inventory (库存表)
CREATE TABLE agent_data.inventory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id VARCHAR(255) NOT NULL UNIQUE,
    warehouse_id VARCHAR(255),
    quantity INTEGER,
    reserved_quantity INTEGER,
    reorder_level INTEGER,
    last_reorder_date DATE,
    forecast_demand JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_inventory_product_id ON agent_data.inventory(product_id);
CREATE INDEX idx_inventory_warehouse_id ON agent_data.inventory(warehouse_id);

-- 初始化 Agent 配置
INSERT INTO agent_data.agent_config (agent_id, agent_name, agent_description, version, config, status)
VALUES (
    'retail-agent',
    '零售电商智能体',
    '专注于智能推荐、用户画像、库存管理的AI智能体',
    '1.0.0',
    '{
        "features": [
            "intelligent_recommendation",
            "user_profiling",
            "inventory_management"
        ],
        "supported_recommendation_types": [
            "personalized",
            "collaborative",
            "content_based",
            "hybrid"
        ]
    }'::jsonb,
    'ACTIVE'
);

COMMIT;
