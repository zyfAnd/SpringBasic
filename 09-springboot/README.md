# 09-Spring Boot模块

## 模块概述
本模块学习Spring Boot快速开发，简化Spring应用的配置与部署，体验自动化配置和约定优于配置的理念。

## 详细操作步骤

### 1. 必须的依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.0</version>
</parent>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 2. 主要使用的注解

#### @SpringBootApplication
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

**作用：**
- 组合了@Configuration、@EnableAutoConfiguration、@ComponentScan
- Spring Boot应用的入口注解
- 启用自动配置和组件扫描

#### @EnableAutoConfiguration
```java
@Configuration
@EnableAutoConfiguration
public class AppConfig {
    // 启用自动配置
}
```

**作用：**
- 启用Spring Boot的自动配置功能
- 根据classpath中的依赖自动配置Bean
- 遵循约定优于配置的原则

### 3. 核心类及实现原理

#### SpringApplication
```java
SpringApplication app = new SpringApplication(Application.class);
app.run(args);
```

**核心功能：**
- Spring Boot应用的启动类
- 提供应用启动的完整流程
- 支持多种运行模式

#### 自动配置原理
```java
// Spring Boot通过@Conditional注解实现条件化配置
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {
    // 自动配置数据源
}
```

## 学习要点

1. **理解自动配置**：@Conditional注解的使用
2. **掌握启动器**：spring-boot-starter-*的使用
3. **了解配置属性**：application.properties/yml配置
4. **理解内嵌容器**：Tomcat、Jetty等

---

**相关链接：**
- [主项目README](../README.md)
- [08-事务管理模块](../08-transaction/README.md)
- [10-Spring Security模块](../10-security/README.md) 