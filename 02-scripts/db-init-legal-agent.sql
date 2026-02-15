-- 法律咨询智能体数据库初始化脚本
-- Database: openagent_legal_agent

\c postgres;

-- 创建数据库
DROP DATABASE IF EXISTS openagent_legal_agent;
CREATE DATABASE openagent_legal_agent ENCODING 'UTF8';

\c openagent_legal_agent;

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

-- 法律专用表: cases (案例库)
CREATE TABLE agent_data.cases (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    case_id VARCHAR(255) UNIQUE NOT NULL,
    case_name VARCHAR(500) NOT NULL,
    case_type VARCHAR(100),
    case_level VARCHAR(50),
    court VARCHAR(255),
    decision_date DATE,
    keywords TEXT[],
    summary TEXT,
    full_text TEXT,
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_cases_case_id ON agent_data.cases(case_id);
CREATE INDEX idx_cases_case_type ON agent_data.cases(case_type);
CREATE INDEX idx_cases_keywords ON agent_data.cases USING gin (keywords);
CREATE INDEX idx_cases_vector ON agent_data.cases USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- 法律专用表: laws (法规库)
CREATE TABLE agent_data.laws (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    law_id VARCHAR(255) UNIQUE NOT NULL,
    law_name VARCHAR(500) NOT NULL,
    law_type VARCHAR(100),
    level VARCHAR(50),
    effective_date DATE,
    department VARCHAR(255),
    content TEXT,
    keywords TEXT[],
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_laws_law_id ON agent_data.laws(law_id);
CREATE INDEX idx_laws_law_type ON agent_data.laws(law_type);
CREATE INDEX idx_laws_keywords ON agent_data.laws USING gin (keywords);
CREATE INDEX idx_laws_vector ON agent_data.laws USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- 法律专用表: contracts (合同库)
CREATE TABLE agent_data.contracts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_id VARCHAR(255) UNIQUE NOT NULL,
    contract_name VARCHAR(500) NOT NULL,
    contract_type VARCHAR(100),
    parties TEXT[],
    status VARCHAR(50),
    content TEXT,
    risk_points JSONB,
    compliance_score FLOAT8,
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_contracts_contract_id ON agent_data.contracts(contract_id);
CREATE INDEX idx_contracts_contract_type ON agent_data.contracts(contract_type);
CREATE INDEX idx_contracts_parties ON agent_data.contracts USING gin (parties);
CREATE INDEX idx_contracts_vector ON agent_data.contracts USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- 初始化 Agent 配置
INSERT INTO agent_data.agent_config (agent_id, agent_name, agent_description, version, config, status)
VALUES (
    'legal-agent',
    '法律咨询智能体',
    '专注于法律咨询、合同审查、法规检索的AI智能体',
    '1.0.0',
    '{
        "features": [
            "legal_consultation",
            "contract_review",
            "law_search"
        ],
        "supported_contract_types": [
            "employment_contract",
            "sales_contract",
            "lease_contract"
        ]
    }'::jsonb,
    'ACTIVE'
);

COMMIT;
