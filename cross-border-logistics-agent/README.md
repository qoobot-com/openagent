# Cross Border Logistics Agent

跨境物流优化AI Agent系统

## 模块列表

### 1. Dynamic Route Planning Agent

- **业务设计**: 处理Dynamic Route Planning相关业务逻辑
- **应用设计**: 提供Dynamic Route Planning相关API接口
- **数据设计**: 管理Dynamic Route Planning相关数据
- **技术设计**: 使用MCP协议进行系统集成

### 2. Exception Handling Agent

- **业务设计**: 处理Exception Handling相关业务逻辑
- **应用设计**: 提供Exception Handling相关API接口
- **数据设计**: 管理Exception Handling相关数据
- **技术设计**: 使用MCP协议进行系统集成

### 3. Cost Optimization Agent

- **业务设计**: 处理Cost Optimization相关业务逻辑
- **应用设计**: 提供Cost Optimization相关API接口
- **数据设计**: 管理Cost Optimization相关数据
- **技术设计**: 使用MCP协议进行系统集成


## 技术栈

- Spring Boot 3.5.10
- LangChain4J 0.34.0
- MyBatis Plus 3.5.7
- PostgreSQL
- Redis
- Kafka
- MCP协议

## 快速开始

### 构建项目

```bash
mvn clean install
```

### 启动服务

```bash
mvn spring-boot:run
```

### 访问API

- Swagger UI: http://localhost:8080/swagger-ui.html
- Actuator: http://localhost:8080/actuator/health

## API文档

详细API文档请参考Swagger UI。

## 配置说明

配置文件位于 `src/main/resources/application.yml`。

## 开发指南

请参考[开发文档](../../docs/垂直领域AI Agent商业设计.md)获取详细信息。

## 许可证

MIT License
