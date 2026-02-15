# Medical Diagnosis Agent

智能医疗诊断AI Agent系统

## 模块列表

### 1. Medical Record Analysis Agent

- **业务设计**: 处理Medical Record Analysis相关业务逻辑
- **应用设计**: 提供Medical Record Analysis相关API接口
- **数据设计**: 管理Medical Record Analysis相关数据
- **技术设计**: 使用MCP协议进行系统集成

### 2. Multidisciplinary Collaboration Agent

- **业务设计**: 处理Multidisciplinary Collaboration相关业务逻辑
- **应用设计**: 提供Multidisciplinary Collaboration相关API接口
- **数据设计**: 管理Multidisciplinary Collaboration相关数据
- **技术设计**: 使用MCP协议进行系统集成

### 3. Treatment Optimization Agent

- **业务设计**: 处理Treatment Optimization相关业务逻辑
- **应用设计**: 提供Treatment Optimization相关API接口
- **数据设计**: 管理Treatment Optimization相关数据
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
