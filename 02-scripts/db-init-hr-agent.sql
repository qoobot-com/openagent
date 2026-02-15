-- 人力资源管理智能体数据库初始化脚本
-- Database: openagent_hr_agent

\c postgres;

-- 创建数据库
DROP DATABASE IF EXISTS openagent_hr_agent;
CREATE DATABASE openagent_hr_agent ENCODING 'UTF8';

\c openagent_hr_agent;

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

-- HR专用表: candidates (候选人表)
CREATE TABLE agent_data.candidates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(50),
    experience_years INTEGER,
    education VARCHAR(255),
    skills TEXT[],
    past_companies TEXT[],
    resume_text TEXT,
    embedding vector(1536),
    status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_candidates_candidate_id ON agent_data.candidates(candidate_id);
CREATE INDEX idx_candidates_status ON agent_data.candidates(status);
CREATE INDEX idx_candidates_skills ON agent_data.candidates USING gin (skills);
CREATE INDEX idx_candidates_vector ON agent_data.candidates USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- HR专用表: positions (职位表)
CREATE TABLE agent_data.positions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    position_id VARCHAR(255) UNIQUE NOT NULL,
    position_name VARCHAR(500) NOT NULL,
    department VARCHAR(255),
    level VARCHAR(50),
    requirements TEXT[],
    responsibilities TEXT[],
    salary_range VARCHAR(100),
    embedding vector(1536),
    status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_positions_position_id ON agent_data.positions(position_id);
CREATE INDEX idx_positions_department ON agent_data.positions(department);
CREATE INDEX idx_positions_status ON agent_data.positions(status);
CREATE INDEX idx_positions_vector ON agent_data.positions USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- HR专用表: employees (员工表)
CREATE TABLE agent_data.employees (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    department VARCHAR(255),
    position VARCHAR(255),
    hire_date DATE,
    salary DECIMAL(12,2),
    skills TEXT[],
    performance_scores JSONB,
    training_records JSONB,
    embedding vector(1536),
    status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_employees_employee_id ON agent_data.employees(employee_id);
CREATE INDEX idx_employees_department ON agent_data.employees(department);
CREATE INDEX idx_employees_status ON agent_data.employees(status);
CREATE INDEX idx_employees_vector ON agent_data.employees USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- HR专用表: performance_reviews (绩效评估表)
CREATE TABLE agent_data.performance_reviews (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    review_id VARCHAR(255) UNIQUE NOT NULL,
    employee_id VARCHAR(255) NOT NULL,
    reviewer_id VARCHAR(255),
    review_period VARCHAR(50),
    overall_score FLOAT8,
    criteria_scores JSONB,
    strengths TEXT[],
    improvement_areas TEXT[],
    goals TEXT[],
    feedback TEXT,
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_performance_reviews_review_id ON agent_data.performance_reviews(review_id);
CREATE INDEX idx_performance_reviews_employee_id ON agent_data.performance_reviews(employee_id);
CREATE INDEX idx_performance_reviews_review_period ON agent_data.performance_reviews(review_period);
CREATE INDEX idx_performance_reviews_vector ON agent_data.performance_reviews USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- HR专用表: recruitment_records (招聘记录表)
CREATE TABLE agent_data.recruitment_records (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    record_id VARCHAR(255) UNIQUE NOT NULL,
    candidate_id VARCHAR(255) NOT NULL,
    position_id VARCHAR(255) NOT NULL,
    stage VARCHAR(50),
    interview_scores JSONB,
    feedback TEXT,
    offer_status VARCHAR(50),
    match_score FLOAT8,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_recruitment_records_record_id ON agent_data.recruitment_records(record_id);
CREATE INDEX idx_recruitment_records_candidate_id ON agent_data.recruitment_records(candidate_id);
CREATE INDEX idx_recruitment_records_position_id ON agent_data.recruitment_records(position_id);
CREATE INDEX idx_recruitment_records_stage ON agent_data.recruitment_records(stage);

-- 初始化 Agent 配置
INSERT INTO agent_data.agent_config (agent_id, agent_name, agent_description, version, config, status)
VALUES (
    'hr-agent',
    '人力资源管理智能体',
    '专注于智能招聘、员工管理、绩效评估的AI智能体',
    '1.0.0',
    '{
        "features": [
            "intelligent_recruitment",
            "employee_management",
            "performance_evaluation"
        ],
        "supported_recruitment_stages": [
            "resume_screening",
            "interview",
            "assessment",
            "offer"
        ]
    }'::jsonb,
    'ACTIVE'
);

COMMIT;
