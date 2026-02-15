# 教育培训智能体 (Education Agent)

## 概述

专注于在线教育、智能辅导、学习路径规划的AI智能体系统。

## 功能模块

### 1. 智能辅导
- 个性化学习推荐
- 知识点讲解
- 作业批改

### 2. 学习路径规划
- 学习计划制定
- 进度跟踪
- 能力评估

### 3. 内容生成
- 试题生成
- 学习资料推荐
- 知识图谱构建

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
psql -U postgres -h localhost -f 02-scripts/db-init-education-agent.sql
```

### 3. 配置环境变量

```bash
export OPENAI_API_KEY=your-api-key
export DATABASE_URL=jdbc:postgresql://localhost:5432/openagent_education_agent
```

### 4. 启动应用

```bash
mvn spring-boot:run
```

## API 文档

访问 http://localhost:8202/education-agent/swagger-ui.html 查看完整 API 文档。

## 端口配置

- 应用端口: 8202
- 上下文路径: /education-agent

## License

MIT License
