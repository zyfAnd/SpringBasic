# 07-数据访问模块

## 模块概述
本模块涵盖Spring数据访问技术，包括Spring JDBC、事务管理、与MyBatis等ORM框架的集成。

## 详细操作步骤

### 1. 必须的依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.23</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
</dependency>
<dependency>
    <groupId>org.mybatis.spring</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.7</version>
</dependency>
```

### 2. 主要使用的注解

#### @Transactional
```java
@Service
public class UserService {
    @Transactional
    public void createUser(User user) {
        // 事务方法
    }
}
```

**作用：**
- 声明式事务管理
- 自动开启和提交事务
- 异常时自动回滚

#### @Repository
```java
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
```

**作用：**
- 标识数据访问层组件
- 提供数据访问异常转换

### 3. 核心类及实现原理

#### JdbcTemplate
```java
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public User findById(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM users WHERE id = ?", 
            new Object[]{id}, 
            new UserRowMapper()
        );
    }
}
```

**核心功能：**
- 简化JDBC操作
- 自动处理资源管理
- 提供异常转换

#### DataSource
```java
@Bean
public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/test");
    dataSource.setUsername("root");
    dataSource.setPassword("password");
    return dataSource;
}
```

## 学习要点

1. **理解Spring JDBC**：JdbcTemplate的使用
2. **掌握事务管理**：@Transactional注解
3. **了解ORM集成**：MyBatis、Hibernate等
4. **理解数据源配置**：DataSource配置

---

**相关链接：**
- [主项目README](../README.md)
- [06-Spring MVC模块](../06-spring-mvc/README.md)
- [08-事务管理模块](../08-transaction/README.md) 