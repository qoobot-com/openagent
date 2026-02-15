# 法律咨询智能体 (Legal Agent)

## 概述

专注于法律咨询、合同审查、法规检索的AI智能体系统。

## 功能模块

### 1. 法律咨询
- 智能问答
- 案例分析
- 法律条文检索

### 2. 合同审查
- 合同条款分析
- 风险识别
- 合规性检查

### 3. 法规检索
- 最新法规查询
- 法规解读
- 跨地区对比

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
psql -U postgres -h localhost -f 02-scripts/db-init-legal-agent.sql
```

### 3. 配置环境变量

```bash
export OPENAI_API_KEY=your-api-key
export DATABASE_URL=jdbc:postgresql://localhost:5432/openagent_legal_agent
```

### 4. 启动应用

```bash
mvn spring-boot:run
```

## API 文档

访问 http://localhost:8201/legal-agent/swagger-ui.html 查看完整 API 文档。

## 端口配置

- 应用端口: 8201
- 上下文路径: /legal-agent

## License

MIT License
