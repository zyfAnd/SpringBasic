# 10-Spring Security模块

## 模块概述
本模块学习Spring Security安全认证与授权机制，掌握常见的安全配置与自定义扩展。

## 详细操作步骤

### 1. 必须的依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 2. 主要使用的注解

#### @EnableWebSecurity
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 安全配置
}
```

**作用：**
- 启用Spring Security Web安全功能
- 提供默认的安全配置
- 支持自定义安全规则

#### @PreAuthorize
```java
@Service
public class UserService {
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        // 只有ADMIN角色才能执行
    }
}
```

**作用：**
- 方法级别的权限控制
- 支持SpEL表达式
- 在方法执行前进行权限检查

### 3. 核心类及实现原理

#### WebSecurityConfigurerAdapter
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin();
    }
}
```

**核心功能：**
- 配置HTTP安全规则
- 定义认证和授权策略
- 配置登录和注销行为

## 学习要点

1. **理解认证机制**：用户名密码认证、JWT等
2. **掌握授权控制**：角色、权限管理
3. **了解安全过滤器**：SecurityFilterChain
4. **理解CSRF防护**：跨站请求伪造防护

---

**相关链接：**
- [主项目README](../README.md)
- [09-Spring Boot模块](../09-springboot/README.md)
- [11-集成模块](../11-integration/README.md) 