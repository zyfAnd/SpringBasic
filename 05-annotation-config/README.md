# 05-注解配置模块

## 模块概述
本模块深入讲解Spring基于注解的配置方式，包括各种注解的使用方法、配置类的编写、组件扫描等，体验现代Spring开发的主流方式。

## 详细操作步骤

### 1. 必须的依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>5.3.23</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.19</version>
</dependency>
```

**依赖说明：**
- `spring-context`: Spring核心容器，支持注解配置
- `spring-aop`: AOP功能支持
- `aspectjweaver`: AspectJ织入器

### 2. 主要使用的注解

#### @Configuration
```java
@Configuration
@ComponentScan("com.kg2s")
@EnableAspectJAutoProxy
public class AppConfig {
    // 配置类，替代XML配置文件
}
```

**作用：**
- 标识一个类为Spring配置类
- 替代传统的XML配置文件
- 可以包含@Bean方法定义
- 支持组件扫描和AOP配置

#### @ComponentScan
```java
@Configuration
@ComponentScan(basePackages = "com.kg2s")
public class AppConfig {
    // 扫描指定包下的组件
}
```

**作用：**
- 指定Spring扫描的包路径
- 自动发现并注册@Component注解的类
- 支持basePackages、basePackageClasses等属性
- 可以排除特定的类或包

#### @EnableAspectJAutoProxy
```java
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    // 启用AOP功能
}
```

**作用：**
- 启用Spring AOP功能
- 自动创建代理对象
- 支持@Aspect注解的切面类

#### @Service
```java
@Service
public class UserServiceImpl implements UserService {
    // 服务层实现类
}
```

**作用：**
- 标识一个类为服务层组件
- 是@Component的特殊化
- 语义化标识，便于理解代码结构
- 默认bean名称为类名首字母小写

#### @Repository
```java
@Repository
public class UserRepositoryImpl implements UserRepository {
    // 数据访问层实现类
}
```

**作用：**
- 标识一个类为数据访问层组件
- 是@Component的特殊化
- 提供数据访问异常转换功能
- 便于Spring识别数据访问层组件

#### @Controller
```java
@Controller
public class UserController {
    // 控制器类
}
```

**作用：**
- 标识一个类为控制器组件
- 是@Component的特殊化
- 通常与Spring MVC配合使用
- 处理HTTP请求和响应

#### @Bean
```java
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }
    
    @Bean
    public UserService userService(DataSource dataSource) {
        return new UserServiceImpl(dataSource);
    }
}
```

**作用：**
- 在配置类中定义Bean
- 方法名默认为Bean名称
- 支持依赖注入
- 可以指定Bean的作用域和初始化方法

### 3. 核心类及实现原理

#### AnnotationConfigApplicationContext
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```

**核心功能：**
- 基于注解的ApplicationContext实现
- 支持@Configuration、@Component等注解
- 提供现代化的Spring配置方式

**实现原理：**
1. **配置类解析**：解析@Configuration注解的类
2. **组件扫描**：扫描@ComponentScan指定的包
3. **Bean注册**：将发现的Bean注册到容器中
4. **依赖解析**：解析Bean之间的依赖关系

#### 组件扫描机制
```java
@ComponentScan(basePackages = "com.kg2s", 
               includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
               excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Test.*"))
public class AppConfig {
    // 配置组件扫描
}
```

**扫描机制：**
1. **包扫描**：扫描指定包及其子包
2. **注解过滤**：根据注解类型过滤组件
3. **正则过滤**：根据类名模式过滤组件
4. **Bean注册**：将符合条件的类注册为Bean

### 4. 注解配置的优势

#### 类型安全
```java
// 注解配置：编译时检查
@Autowired
private UserService userService; // 编译时检查类型

// XML配置：运行时检查
<property name="userService" ref="userService"/> // 运行时检查
```

#### 重构友好
```java
// 修改类名时，IDE自动更新引用
@Service
public class UserServiceImpl implements UserService {
    // 修改类名时，@Autowired会自动更新
}
```

#### 代码可读性
```java
// 注解配置：配置与代码在一起，便于理解
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}

// XML配置：配置与代码分离，需要对照查看
```

### 5. 配置类的最佳实践

#### 模块化配置
```java
@Configuration
@ComponentScan("com.kg2s.service")
public class ServiceConfig {
    // 服务层配置
}

@Configuration
@ComponentScan("com.kg2s.repository")
public class RepositoryConfig {
    // 数据访问层配置
}

@Configuration
@Import({ServiceConfig.class, RepositoryConfig.class})
public class AppConfig {
    // 主配置类，导入其他配置
}
```

#### 条件化配置
```java
@Configuration
@ConditionalOnClass(DataSource.class)
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }
}
```

#### 配置属性注入
```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Value("${database.url}")
    private String databaseUrl;
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(databaseUrl);
        return dataSource;
    }
}
```

## 运行示例

```java
@Configuration
@ComponentScan("com.kg2s")
@EnableAspectJAutoProxy
public class AppConfig {
    // 主配置类
}

public class Main {
    public static void main(String[] args) {
        // 创建基于注解的Spring容器
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // 获取Bean
        UserService userService = context.getBean(UserService.class);
        UserController userController = context.getBean(UserController.class);
        
        // 使用Bean
        userService.createUser("张三");
        userController.handleRequest();
    }
}
```

## 与XML配置的对比

| 特性 | 注解配置 | XML配置 |
|------|----------|---------|
| 类型安全 | 编译时检查 | 运行时检查 |
| 重构支持 | IDE自动更新 | 需要手动更新 |
| 可读性 | 配置与代码在一起 | 配置与代码分离 |
| 配置集中度 | 分散在代码中 | 集中在XML文件中 |
| 学习曲线 | 需要了解注解 | 需要了解XML语法 |
| 动态修改 | 需要重新编译 | 可以动态修改 |

## 最佳实践

### 1. 合理使用组件注解
```java
// 服务层
@Service
public class UserService {
    // 业务逻辑
}

// 数据访问层
@Repository
public class UserRepository {
    // 数据访问逻辑
}

// 控制器层
@Controller
public class UserController {
    // 请求处理逻辑
}

// 通用组件
@Component
public class UtilityService {
    // 通用工具逻辑
}
```

### 2. 配置类模块化
```java
// 按功能模块划分配置类
@Configuration
@ComponentScan("com.kg2s.security")
public class SecurityConfig {
    // 安全相关配置
}

@Configuration
@ComponentScan("com.kg2s.database")
public class DatabaseConfig {
    // 数据库相关配置
}
```

### 3. 使用@Import组合配置
```java
@Configuration
@Import({SecurityConfig.class, DatabaseConfig.class})
@ComponentScan("com.kg2s")
public class AppConfig {
    // 主配置类，组合其他配置
}
```

## 学习要点

1. **理解注解配置的优势**：类型安全、重构友好、可读性好
2. **掌握各种组件注解**：@Service、@Repository、@Controller、@Component
3. **了解配置类注解**：@Configuration、@ComponentScan、@EnableAspectJAutoProxy
4. **掌握@Bean方法**：在配置类中定义Bean
5. **理解组件扫描机制**：包扫描、过滤规则、Bean注册

---

**相关链接：**
- [主项目README](../README.md)
- [04-AOP基础模块](../04-aop-basics/README.md)
- [06-Spring MVC模块](../06-spring-mvc/README.md)