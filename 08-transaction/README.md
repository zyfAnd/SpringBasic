# 08-事务管理模块

## 模块概述
本模块深入讲解Spring事务管理，理解声明式事务、编程式事务及其配置方法，掌握事务的传播行为和隔离级别。

## 详细操作步骤

### 1. 必须的依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>5.3.23</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.23</version>
</dependency>
```

### 2. 主要使用的注解

#### @Transactional
```java
@Service
public class UserService {
    @Transactional(propagation = Propagation.REQUIRED, 
                   isolation = Isolation.READ_COMMITTED,
                   rollbackFor = Exception.class)
    public void createUser(User user) {
        // 事务方法
    }
}
```

**作用：**
- 声明式事务管理
- 支持传播行为配置
- 支持隔离级别配置
- 支持回滚规则配置

#### @EnableTransactionManagement
```java
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    // 启用事务管理
}
```

**作用：**
- 启用Spring事务管理功能
- 自动扫描@Transactional注解
- 创建事务代理对象

### 3. 核心类及实现原理

#### TransactionManager
```java
@Bean
public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
}
```

**核心功能：**
- 管理事务的开启、提交、回滚
- 支持不同数据源的事务管理
- 提供事务状态管理

#### 事务传播行为
```java
// REQUIRED：默认传播行为，如果当前存在事务则加入，否则创建新事务
@Transactional(propagation = Propagation.REQUIRED)

// REQUIRES_NEW：总是创建新事务
@Transactional(propagation = Propagation.REQUIRES_NEW)

// SUPPORTS：如果当前存在事务则加入，否则以非事务方式执行
@Transactional(propagation = Propagation.SUPPORTS)
```

## 学习要点

1. **理解事务概念**：ACID特性
2. **掌握传播行为**：REQUIRED、REQUIRES_NEW等
3. **了解隔离级别**：READ_UNCOMMITTED、READ_COMMITTED等
4. **理解事务代理**：AOP实现事务管理

---

**相关链接：**
- [主项目README](../README.md)
- [07-数据访问模块](../07-data-access/README.md)
- [09-Spring Boot模块](../09-springboot/README.md) 