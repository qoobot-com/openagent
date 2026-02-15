# Industrial Quality Agent

工业质检AI Agent系统

## 模块列表

### 1. Defect Detection Agent

- **业务设计**: 处理Defect Detection相关业务逻辑
- **应用设计**: 提供Defect Detection相关API接口
- **数据设计**: 管理Defect Detection相关数据
- **技术设计**: 使用MCP协议进行系统集成

### 2. Quality Standard Management Agent

- **业务设计**: 处理Quality Standard Management相关业务逻辑
- **应用设计**: 提供Quality Standard Management相关API接口
- **数据设计**: 管理Quality Standard Management相关数据
- **技术设计**: 使用MCP协议进行系统集成

### 3. Quality Analysis Agent

- **业务设计**: 处理Quality Analysis相关业务逻辑
- **应用设计**: 提供Quality Analysis相关API接口
- **数据设计**: 管理Quality Analysis相关数据
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
