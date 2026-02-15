# 零售电商智能体 (Retail Agent)

## 概述

专注于智能推荐、用户画像、库存管理的AI智能体系统。

## 功能模块

### 1. 智能推荐
- 个性化商品推荐
- 关联商品推荐
- 场景化推荐

### 2. 用户画像
- 用户行为分析
- 兴趣标签提取
- 分层画像构建

### 3. 库存管理
- 需求预测
- 补货策略
- 库存优化

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
psql -U postgres -h localhost -f 02-scripts/db-init-retail-agent.sql
```

### 3. 配置环境变量

```bash
export OPENAI_API_KEY=your-api-key
export DATABASE_URL=jdbc:postgresql://localhost:5432/openagent_retail_agent
```

### 4. 启动应用

```bash
mvn spring-boot:run
```

## API 文档

访问 http://localhost:8203/retail-agent/swagger-ui.html 查看完整 API 文档。

## 端口配置

- 应用端口: 8203
- 上下文路径: /retail-agent

## License

MIT License
