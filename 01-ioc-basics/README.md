# 01-IoC基础模块

## 模块概述
本模块演示Spring IoC（控制反转）的基本概念和实现原理，通过简单的示例理解Spring容器的核心机制。

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
- `spring-context`: Spring的核心容器模块，提供IoC容器的基本功能
- 包含了`ApplicationContext`、`BeanFactory`等核心接口
- 自动包含`spring-core`、`spring-beans`、`spring-aop`等基础依赖

### 2. 主要使用的注解

#### @Component
```java
@Component
public class UserDao {
    // 类实现
}
```

**作用：**
- 标识一个类为Spring管理的组件
- 告诉Spring容器这个类需要被实例化和管理
- 默认bean名称为类名首字母小写（如：userDao）

#### @Configuration
```java
@Configuration
public class SpringConfig {
    // 配置类
}
```

**作用：**
- 标识一个类为Spring配置类
- 替代传统的XML配置文件
- 可以包含@Bean方法定义

#### @ComponentScan
```java
@Configuration
@ComponentScan("com.kg2s")
public class SpringConfig {
    // 扫描指定包下的组件
}
```

**作用：**
- 指定Spring扫描的包路径
- 自动发现并注册@Component注解的类
- 支持basePackages、basePackageClasses等属性

### 3. 核心类及实现原理

#### ApplicationContext
```java
ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
```

**核心功能：**
- Spring IoC容器的主要实现
- 负责Bean的创建、配置和管理
- 提供Bean的获取、生命周期管理等功能

**实现原理：**
1. **Bean定义解析**：解析配置类中的@Bean方法和@Component注解
2. **Bean实例化**：通过反射创建Bean实例
3. **依赖注入**：自动注入Bean之间的依赖关系
4. **生命周期管理**：管理Bean的初始化、销毁等生命周期

#### BeanFactory
```java
BeanFactory beanFactory = context.getBeanFactory();
```

**核心功能：**
- Spring IoC容器的根接口
- 提供Bean的基本操作功能
- ApplicationContext继承自BeanFactory，提供更多企业级功能

**实现原理：**
- 使用工厂模式管理Bean的创建和获取
- 维护Bean定义和实例的映射关系
- 提供Bean作用域管理

#### AnnotationConfigApplicationContext
```java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
context.register(SpringConfig.class);
context.refresh();
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

## 运行示例

```java
public class Main {
    public static void main(String[] args) {
        // 创建Spring容器
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        // 从容器中获取Bean
        UserDao userDao = context.getBean(UserDao.class);
        
        // 使用Bean
        userDao.save();
    }
}
```

## 核心概念

### IoC（控制反转）
- **传统方式**：对象自己创建依赖的对象
- **IoC方式**：由Spring容器负责创建和管理对象
- **优势**：降低耦合度，提高代码的可维护性和可测试性

### DI（依赖注入）
- **概念**：Spring容器在创建Bean时，自动注入其依赖的其他Bean
- **方式**：构造器注入、Setter注入、字段注入
- **实现**：通过反射机制实现自动注入

### Bean生命周期
1. **实例化**：创建Bean实例
2. **属性设置**：设置Bean的属性值
3. **初始化**：调用初始化方法
4. **使用**：Bean可以被使用
5. **销毁**：调用销毁方法

## 学习要点

1. **理解IoC容器的作用**：管理对象的创建和依赖关系
2. **掌握注解配置**：使用@Configuration、@Component等注解
3. **了解Bean的生命周期**：从创建到销毁的完整过程
4. **理解依赖注入机制**：自动注入依赖对象

---

**相关链接：**
- [主项目README](../README.md)
- [02-依赖注入模块](../02-dependency-injection/README.md)
- [03-Bean生命周期模块](../03-bean-lifecycle/README.md) 