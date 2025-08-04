# 01-IoC基础模块（XML配置方式）

## 模块概述
本模块演示使用XML配置文件实现Spring IoC的传统方式，通过XML配置理解Spring容器的配置机制，为后续注解配置方式做对比。

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
- `spring-context`: Spring的核心容器模块
- 支持XML配置和注解配置两种方式
- 提供ClassPathXmlApplicationContext用于加载XML配置

### 2. XML配置文件结构

#### beans.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Bean定义 -->
    <bean id="simpleGreetingService" class="com.kg2s.service.impl.SimpleGreetingService">
        <property name="prefix" value="Mr "/>
    </bean>
    
    <bean id="formalGreetingService" class="com.kg2s.service.impl.FormalGreetingService"/>
    
    <!-- 构造器注入 -->
    <bean id="controller1" class="com.kg2s.controller.GreetingController">
        <constructor-arg ref="simpleGreetingService"/>
    </bean>
    
    <!-- Setter注入 -->
    <bean id="controller2" class="com.kg2s.controller.GreetingController">
        <property name="greetingService" ref="simpleGreetingService"/>
    </bean>
</beans>
```

**配置说明：**
- `<beans>`: 根元素，包含所有Bean定义
- `<bean>`: 定义单个Bean
- `id`: Bean的唯一标识符
- `class`: Bean的完整类名
- `<property>`: 属性注入配置
- `<constructor-arg>`: 构造器参数注入配置

### 3. 核心类及实现原理

#### ClassPathXmlApplicationContext
```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
```

**核心功能：**
- 从类路径加载XML配置文件的ApplicationContext实现
- 解析XML配置文件中的Bean定义
- 创建和管理Bean实例

**实现原理：**
1. **XML解析**：使用DOM或SAX解析XML配置文件
2. **Bean定义注册**：将XML中的Bean定义转换为BeanDefinition对象
3. **Bean实例化**：根据Bean定义创建Bean实例
4. **依赖注入**：根据XML配置注入依赖关系

#### Bean定义元素

**基本Bean定义：**
```xml
<bean id="beanName" class="com.example.MyClass"/>
```

**属性注入：**
```xml
<bean id="beanName" class="com.example.MyClass">
    <property name="propertyName" value="propertyValue"/>
    <property name="refProperty" ref="otherBean"/>
</bean>
```

**构造器注入：**
```xml
<bean id="beanName" class="com.example.MyClass">
    <constructor-arg value="stringValue"/>
    <constructor-arg ref="otherBean"/>
</bean>
```

**集合注入：**
```xml
<bean id="beanName" class="com.example.MyClass">
    <property name="listProperty">
        <list>
            <value>item1</value>
            <value>item2</value>
        </list>
    </property>
    <property name="mapProperty">
        <map>
            <entry key="key1" value="value1"/>
            <entry key="key2" value="value2"/>
        </map>
    </property>
</bean>
```

### 4. 依赖注入方式

#### Setter注入
```xml
<bean id="controller" class="com.kg2s.controller.GreetingController">
    <property name="greetingService" ref="greetingService"/>
</bean>
```

**特点：**
- 通过setter方法注入依赖
- 可以注入基本类型、引用类型、集合类型
- 支持延迟注入

#### 构造器注入
```xml
<bean id="controller" class="com.kg2s.controller.GreetingController">
    <constructor-arg ref="greetingService"/>
</bean>
```

**特点：**
- 通过构造器参数注入依赖
- 确保Bean创建时就有必要的依赖
- 支持多参数构造器

#### 自动装配
```xml
<bean id="controller" class="com.kg2s.controller.GreetingController" autowire="byType"/>
```

**自动装配模式：**
- `byType`: 按类型自动装配
- `byName`: 按名称自动装配
- `constructor`: 构造器自动装配
- `default`: 默认自动装配

## 运行示例

```java
public class Main {
    public static void main(String[] args) {
        // 加载XML配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        
        // 获取Bean实例
        GreetingController controller = context.getBean("controller1", GreetingController.class);
        
        // 使用Bean
        controller.sayHello("World");
    }
}
```

## XML配置的优势与劣势

### 优势
1. **配置集中**：所有配置在一个文件中，便于管理
2. **松耦合**：配置与代码分离
3. **动态修改**：可以在不修改代码的情况下修改配置
4. **工具支持**：IDE提供良好的XML编辑支持

### 劣势
1. **冗长**：XML配置相对冗长
2. **类型安全**：编译时无法检查类型错误
3. **重构困难**：类名修改后需要手动更新XML
4. **可读性差**：大量XML配置可读性较差

## 与注解配置的对比

| 特性 | XML配置 | 注解配置 |
|------|---------|----------|
| 配置方式 | 外部XML文件 | 代码内注解 |
| 类型安全 | 运行时检查 | 编译时检查 |
| 可读性 | 较差 | 较好 |
| 重构支持 | 需要手动更新 | IDE自动支持 |
| 配置集中度 | 集中 | 分散 |

## 学习要点

1. **理解XML配置结构**：掌握beans.xml的基本结构
2. **掌握依赖注入配置**：学会配置Setter注入和构造器注入
3. **了解Bean作用域**：理解singleton、prototype等作用域
4. **对比配置方式**：理解XML配置与注解配置的差异

---

**相关链接：**
- [主项目README](../README.md)
- [01-IoC基础模块（注解）](../01-ioc-basics/README.md)
- [02-依赖注入模块](../02-dependency-injection/README.md) 