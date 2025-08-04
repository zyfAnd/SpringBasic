# 02-依赖注入模块

## 模块概述
本模块深入讲解Spring依赖注入（DI）的各种实现方式，包括构造器注入、Setter注入、字段注入、集合注入等，以及自动装配机制。

## 详细操作步骤

### 1. 必须的依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>
```

**依赖说明：**
- `spring-context`: 提供依赖注入的核心功能
- 包含自动装配、Bean生命周期管理等特性
- 支持多种注入方式的实现

### 2. 主要使用的注解

#### @Autowired
```java
@Component
public class UserManager {
    @Autowired
    private MessageService messageService;
    
    @Autowired
    public UserManager(MessageService messageService) {
        this.messageService = messageService;
    }
    
    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
```

**作用：**
- 自动装配依赖的Bean
- 支持字段注入、构造器注入、Setter注入
- 按类型自动查找匹配的Bean
- 可以标注在字段、构造器、Setter方法上

#### @Qualifier
```java
@Component
public class UserManager {
    @Autowired
    @Qualifier("emailService")
    private MessageService messageService;
}
```

**作用：**
- 指定要注入的Bean的名称
- 当有多个相同类型的Bean时，用于消除歧义
- 与@Autowired配合使用

#### @Resource
```java
@Component
public class UserManager {
    @Resource(name = "emailService")
    private MessageService messageService;
}
```

**作用：**
- JSR-250规范定义的依赖注入注解
- 默认按名称注入，也可以按类型注入
- 可以指定name属性指定Bean名称

#### @Value
```java
@Component
public class ConfigService {
    @Value("${app.name:defaultName}")
    private String appName;
    
    @Value("#{systemProperties['user.home']}")
    private String userHome;
}
```

**作用：**
- 注入基本类型值
- 支持SpEL表达式
- 支持外部配置文件的值注入

### 3. 核心类及实现原理

#### 依赖注入容器
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```

**核心功能：**
- 管理Bean的创建和依赖关系
- 自动解析和注入依赖
- 处理循环依赖问题

**实现原理：**
1. **Bean定义解析**：解析@Component等注解
2. **依赖关系分析**：分析Bean之间的依赖关系
3. **依赖注入执行**：在Bean创建时注入依赖
4. **循环依赖处理**：使用三级缓存解决循环依赖

#### 自动装配机制

**按类型自动装配：**
```java
@Autowired
private MessageService messageService; // 查找MessageService类型的Bean
```

**按名称自动装配：**
```java
@Autowired
@Qualifier("emailService")
private MessageService messageService; // 查找名为emailService的Bean
```

**构造器自动装配：**
```java
@Component
public class UserManager {
    private final MessageService messageService;
    
    @Autowired // 可以省略
    public UserManager(MessageService messageService) {
        this.messageService = messageService;
    }
}
```

### 4. 依赖注入方式详解

#### 字段注入
```java
@Component
public class UserManager {
    @Autowired
    private MessageService messageService;
}
```

**特点：**
- 代码简洁，使用方便
- 无法注入final字段
- 不利于单元测试
- 可能产生循环依赖

#### 构造器注入
```java
@Component
public class UserManager {
    private final MessageService messageService;
    
    public UserManager(MessageService messageService) {
        this.messageService = messageService;
    }
}
```

**特点：**
- 确保依赖不可变
- 便于单元测试
- 避免循环依赖
- 推荐使用的方式

#### Setter注入
```java
@Component
public class UserManager {
    private MessageService messageService;
    
    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
```

**特点：**
- 可以注入可选依赖
- 支持运行时修改依赖
- 适合可选依赖的场景

#### 集合注入
```java
@Component
public class BatchNotifier {
    @Autowired
    private List<MessageService> messageServices;
    
    @Autowired
    private Map<String, MessageService> messageServiceMap;
}
```

**特点：**
- 自动注入所有匹配类型的Bean
- 支持List、Set、Map等集合类型
- Map的key为Bean名称

### 5. 循环依赖处理

#### 三级缓存机制
```java
// 一级缓存：存放完全初始化好的Bean
private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

// 二级缓存：存放早期暴露的Bean（未完全初始化）
private final Map<String, Object> earlySingletonObjects = new HashMap<>();

// 三级缓存：存放Bean工厂
private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();
```

**处理流程：**
1. 创建Bean A时，将A的工厂放入三级缓存
2. A依赖B，创建Bean B
3. B依赖A，从三级缓存获取A的早期引用
4. B创建完成，A注入B
5. A创建完成，放入一级缓存

## 运行示例

```java
@Configuration
@ComponentScan("com.kg2s")
public class AppConfig {
    // 配置类
}

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        UserManager userManager = context.getBean(UserManager.class);
        userManager.sendMessage();
        
        BatchNotifier notifier = context.getBean(BatchNotifier.class);
        notifier.sendBatchMessage();
    }
}
```

## 最佳实践

### 1. 推荐使用构造器注入
```java
@Component
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
}
```

### 2. 避免字段注入
```java
// 不推荐
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
}

// 推荐
@Component
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

### 3. 使用@Qualifier消除歧义
```java
@Component
public class UserService {
    @Autowired
    @Qualifier("jdbcUserRepository")
    private UserRepository userRepository;
}
```

## 学习要点

1. **理解依赖注入的原理**：掌握Spring如何自动注入依赖
2. **掌握各种注入方式**：字段注入、构造器注入、Setter注入
3. **了解自动装配机制**：按类型、按名称自动装配
4. **理解循环依赖处理**：三级缓存机制的工作原理
5. **掌握最佳实践**：推荐使用构造器注入

---

**相关链接：**
- [主项目README](../README.md)
- [01-IoC基础模块](../01-ioc-basics/README.md)
- [03-Bean生命周期模块](../03-bean-lifecycle/README.md) 