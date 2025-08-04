# 03-Bean生命周期模块

## 模块概述
本模块深入讲解Spring Bean的完整生命周期，包括实例化、属性设置、初始化、使用、销毁等各个阶段，以及相关的接口和注解。

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
- `spring-context`: 提供Bean生命周期管理的核心功能
- 包含各种生命周期接口和注解
- 支持自定义Bean的初始化和销毁逻辑

### 2. 主要使用的注解

#### @PostConstruct
```java
@Component
public class LifeCycleBean {
    @PostConstruct
    public void postConstruct() {
        System.out.println("9. @PostConstruct 注解方法被调用");
    }
}
```

**作用：**
- JSR-250规范定义的初始化注解
- 在Bean属性设置完成后，初始化方法之前调用
- 用于执行Bean的初始化逻辑

#### @PreDestroy
```java
@Component
public class LifeCycleBean {
    @PreDestroy
    public void preDestroy() {
        System.out.println("12. @PreDestroy 注解方法被调用");
    }
}
```

**作用：**
- JSR-250规范定义的销毁注解
- 在Bean销毁之前调用
- 用于执行清理逻辑，释放资源

#### @Scope
```java
@Component
@Scope("singleton") // 或 "prototype", "request", "session"
public class LifeCycleBean {
    // Bean实现
}
```

**作用：**
- 定义Bean的作用域
- 控制Bean的创建和销毁时机
- 影响Bean的生命周期管理

### 3. 核心类及实现原理

#### 生命周期接口

**BeanNameAware接口：**
```java
@Component
public class LifeCycleBean implements BeanNameAware {
    @Override
    public void setBeanName(String name) {
        System.out.println("3. BeanNameAware.setBeanName() - Bean名称: " + name);
    }
}
```

**作用：**
- 让Bean知道自己在容器中的名称
- 在Bean属性设置之前调用
- 用于Bean的自定义命名逻辑

**BeanFactoryAware接口：**
```java
@Component
public class LifeCycleBean implements BeanFactoryAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("4. BeanFactoryAware.setBeanFactory() - 设置BeanFactory");
    }
}
```

**作用：**
- 让Bean获得BeanFactory的引用
- 在BeanNameAware之后调用
- 用于Bean访问容器功能

**ApplicationContextAware接口：**
```java
@Component
public class LifeCycleBean implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("5. ApplicationContextAware.setApplicationContext() - 设置ApplicationContext");
    }
}
```

**作用：**
- 让Bean获得ApplicationContext的引用
- 在BeanFactoryAware之后调用
- 用于Bean访问应用上下文功能

**InitializingBean接口：**
```java
@Component
public class LifeCycleBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("10. InitializingBean.afterPropertiesSet() - 属性设置完成后的初始化");
    }
}
```

**作用：**
- Spring提供的初始化接口
- 在@PostConstruct之后调用
- 用于Bean的初始化逻辑

**DisposableBean接口：**
```java
@Component
public class LifeCycleBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("11. DisposableBean.destroy() - Bean销毁");
    }
}
```

**作用：**
- Spring提供的销毁接口
- 在@PreDestroy之前调用
- 用于Bean的清理逻辑

### 4. Bean生命周期完整流程

#### 生命周期阶段详解

**1. 实例化阶段：**
```java
public LifeCycleBean() {
    System.out.println("1. LifeCycleBean 构造器被调用 - 实例化");
}
```

**2. 属性设置阶段：**
```java
public void setName(String name) {
    System.out.println("2. setter 方法被调用 - 设置属性 name: " + name);
    this.name = name;
}
```

**3. Aware接口调用阶段：**
- BeanNameAware.setBeanName()
- BeanFactoryAware.setBeanFactory()
- ApplicationContextAware.setApplicationContext()

**4. 初始化阶段：**
- @PostConstruct注解方法
- InitializingBean.afterPropertiesSet()
- 自定义初始化方法（init-method）

**5. 使用阶段：**
- Bean可以被正常使用

**6. 销毁阶段：**
- DisposableBean.destroy()
- @PreDestroy注解方法
- 自定义销毁方法（destroy-method）

### 5. 作用域对生命周期的影响

#### Singleton作用域
```java
@Component
@Scope("singleton")
public class SingletonBean {
    // 单例Bean，容器启动时创建，容器关闭时销毁
}
```

**特点：**
- 容器启动时创建
- 整个应用生命周期内只有一个实例
- 容器关闭时销毁

#### Prototype作用域
```java
@Component
@Scope("prototype")
public class PrototypeBean {
    // 原型Bean，每次获取时创建，不管理销毁
}
```

**特点：**
- 每次获取时创建新实例
- 容器不管理其销毁
- 需要手动清理资源

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
        
        // 获取Bean，触发生命周期
        LifeCycleBean bean = context.getBean(LifeCycleBean.class);
        
        // 使用Bean
        System.out.println("Bean使用中...");
        
        // 关闭容器，触发销毁
        ((AnnotationConfigApplicationContext) context).close();
    }
}
```

## 生命周期回调顺序

### 初始化顺序
1. 构造器调用
2. 属性设置
3. BeanNameAware.setBeanName()
4. BeanFactoryAware.setBeanFactory()
5. ApplicationContextAware.setApplicationContext()
6. BeanPostProcessor.postProcessBeforeInitialization()
7. @PostConstruct注解方法
8. InitializingBean.afterPropertiesSet()
9. 自定义初始化方法
10. BeanPostProcessor.postProcessAfterInitialization()

### 销毁顺序
1. DisposableBean.destroy()
2. @PreDestroy注解方法
3. 自定义销毁方法

## 最佳实践

### 1. 选择合适的初始化方式
```java
@Component
public class DatabaseService {
    // 推荐使用@PostConstruct进行初始化
    @PostConstruct
    public void init() {
        // 初始化数据库连接
    }
    
    // 推荐使用@PreDestroy进行清理
    @PreDestroy
    public void cleanup() {
        // 关闭数据库连接
    }
}
```

### 2. 避免在构造器中执行复杂逻辑
```java
// 不推荐
@Component
public class BadBean {
    public BadBean() {
        // 复杂的初始化逻辑
        initDatabase();
        loadConfiguration();
    }
}

// 推荐
@Component
public class GoodBean {
    @PostConstruct
    public void init() {
        // 复杂的初始化逻辑
        initDatabase();
        loadConfiguration();
    }
}
```

### 3. 合理使用作用域
```java
// 有状态Bean使用prototype
@Component
@Scope("prototype")
public class UserSession {
    private String userId;
    // 用户会话相关逻辑
}

// 无状态Bean使用singleton
@Component
@Scope("singleton")
public class UserService {
    // 用户服务逻辑
}
```

## 学习要点

1. **理解Bean生命周期的完整流程**：从创建到销毁的各个阶段
2. **掌握各种生命周期接口**：Aware接口、InitializingBean、DisposableBean
3. **了解生命周期注解**：@PostConstruct、@PreDestroy
4. **理解作用域对生命周期的影响**：singleton vs prototype
5. **掌握最佳实践**：合理使用生命周期回调

---

**相关链接：**
- [主项目README](../README.md)
- [02-依赖注入模块](../02-dependency-injection/README.md)
- [04-AOP基础模块](../04-aop-basics/README.md) 