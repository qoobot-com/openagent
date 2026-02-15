# 人力资源管理智能体 (HR Agent)

## 概述

专注于智能招聘、员工管理、绩效评估的AI智能体系统。

## 功能模块

### 1. 智能招聘
- 简历筛选
- 面试辅助
- 人才匹配

### 2. 员工管理
- 员工信息管理
- 考勤管理
- 培训管理

### 3. 绩效评估
- 绩效指标分析
- 360度评估
- 发展建议

## 技术栈

- Spring Boot 3.5.10
- Spring AI 2.0
- LangChain4J
- PostgreSQL + pgvector
- Redis
- Kafka

## 快速开始

### 1. 启动数据库

```bash
docker-compose up -d postgres
```

### 2. 初始化数据库

```bash
psql -U postgres -h localhost -f 02-scripts/db-init-hr-agent.sql
```

### 3. 配置环境变量

```bash
export OPENAI_API_KEY=your-api-key
export DATABASE_URL=jdbc:postgresql://localhost:5432/openagent_hr_agent
```

### 4. 启动应用

```bash
mvn spring-boot:run
```

## API 文档

访问 http://localhost:8204/hr-agent/swagger-ui.html 查看完整 API 文档。

## 端口配置

- 应用端口: 8204
- 上下文路径: /hr-agent

## License

MIT License
