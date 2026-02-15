# Investment Advisor Agent

智能投资顾问AI Agent系统

## 模块列表

### 1. Customer Profiling Agent

- **业务设计**: 处理Customer Profiling相关业务逻辑
- **应用设计**: 提供Customer Profiling相关API接口
- **数据设计**: 管理Customer Profiling相关数据
- **技术设计**: 使用MCP协议进行系统集成

### 2. Asset Allocation Agent

- **业务设计**: 处理Asset Allocation相关业务逻辑
- **应用设计**: 提供Asset Allocation相关API接口
- **数据设计**: 管理Asset Allocation相关数据
- **技术设计**: 使用MCP协议进行系统集成

### 3. Dynamic Tuning Agent

- **业务设计**: 处理Dynamic Tuning相关业务逻辑
- **应用设计**: 提供Dynamic Tuning相关API接口
- **数据设计**: 管理Dynamic Tuning相关数据
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
