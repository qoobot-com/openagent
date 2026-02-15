-- 教育培训智能体数据库初始化脚本
-- Database: openagent_education_agent

\c postgres;

-- 创建数据库
DROP DATABASE IF EXISTS openagent_education_agent;
CREATE DATABASE openagent_education_agent ENCODING 'UTF8';

\c openagent_education_agent;

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

-- 教育专用表: courses (课程库)
CREATE TABLE agent_data.courses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    course_id VARCHAR(255) UNIQUE NOT NULL,
    course_name VARCHAR(500) NOT NULL,
    subject VARCHAR(100),
    level VARCHAR(50),
    difficulty INTEGER,
    duration_minutes INTEGER,
    description TEXT,
    objectives TEXT[],
    prerequisites TEXT[],
    content TEXT,
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_courses_course_id ON agent_data.courses(course_id);
CREATE INDEX idx_courses_subject ON agent_data.courses(subject);
CREATE INDEX idx_courses_level ON agent_data.courses(level);
CREATE INDEX idx_courses_vector ON agent_data.courses USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- 教育专用表: students (学生表)
CREATE TABLE agent_data.students (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    grade VARCHAR(50),
    learning_style VARCHAR(50),
    interests TEXT[],
    strengths TEXT[],
    weaknesses TEXT[],
    learning_progress JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_students_student_id ON agent_data.students(student_id);
CREATE INDEX idx_students_grade ON agent_data.students(grade);

-- 教育专用表: learning_paths (学习路径表)
CREATE TABLE agent_data.learning_paths (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    path_id VARCHAR(255) UNIQUE NOT NULL,
    path_name VARCHAR(500) NOT NULL,
    target_level VARCHAR(50),
    subject VARCHAR(100),
    estimated_days INTEGER,
    course_sequence UUID[],
    difficulty_progression JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_learning_paths_path_id ON agent_data.learning_paths(path_id);
CREATE INDEX idx_learning_paths_subject ON agent_data.learning_paths(subject);

-- 教育专用表: assignments (作业表)
CREATE TABLE agent_data.assignments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    assignment_id VARCHAR(255) UNIQUE NOT NULL,
    title VARCHAR(500) NOT NULL,
    course_id VARCHAR(255),
    due_date DATE,
    max_score INTEGER,
    content TEXT,
    embedding vector(1536),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_assignments_assignment_id ON agent_data.assignments(assignment_id);
CREATE INDEX idx_assignments_course_id ON agent_data.assignments(course_id);
CREATE INDEX idx_assignments_vector ON agent_data.assignments USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

-- 初始化 Agent 配置
INSERT INTO agent_data.agent_config (agent_id, agent_name, agent_description, version, config, status)
VALUES (
    'education-agent',
    '教育培训智能体',
    '专注于在线教育、智能辅导、学习路径规划的AI智能体',
    '1.0.0',
    '{
        "features": [
            "intelligent_tutoring",
            "learning_path_planning",
            "content_generation"
        ],
        "supported_subjects": [
            "mathematics",
            "science",
            "language",
            "history"
        ]
    }'::jsonb,
    'ACTIVE'
);

COMMIT;
