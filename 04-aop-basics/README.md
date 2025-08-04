# 04-AOP基础模块

## 模块概述
本模块深入讲解Spring AOP（面向切面编程）的核心概念和实现原理，包括切点、通知、切面、代理机制等，通过实际示例理解AOP的应用场景。

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
- `spring-context`: Spring核心容器
- `spring-aop`: Spring AOP核心功能
- `aspectjweaver`: AspectJ织入器，提供AOP实现

### 2. 主要使用的注解

#### @Aspect
```java
@Component
@Aspect
public class MyAdvice {
    // 切面类，包含切点和通知
}
```

**作用：**
- 标识一个类为切面类
- 告诉Spring这个类包含AOP逻辑
- 需要配合@Component使用才能被Spring管理

#### @Pointcut
```java
@Aspect
@Component
public class MyAdvice {
    @Pointcut("execution(void com.kg2s.UserDao.save())")
    private void pt() {
        // 切点定义，方法体为空
    }
}
```

**作用：**
- 定义切点表达式
- 指定在哪些方法上应用通知
- 可以重复使用，提高代码复用性

#### @Before
```java
@Before("pt()")
public void method() {
    System.out.println(System.currentTimeMillis());
}
```

**作用：**
- 前置通知，在目标方法执行前执行
- 可以获取方法参数，但不能修改返回值
- 适合做日志记录、权限检查等

#### @After
```java
@After("pt()")
public void afterMethod() {
    System.out.println("方法执行完成");
}
```

**作用：**
- 后置通知，在目标方法执行后执行（无论是否异常）
- 适合做清理工作、日志记录等

#### @AfterReturning
```java
@AfterReturning(value = "pt()", returning = "result")
public void afterReturning(Object result) {
    System.out.println("方法正常返回: " + result);
}
```

**作用：**
- 返回通知，在目标方法正常返回后执行
- 可以获取方法的返回值
- 只在方法正常返回时执行

#### @AfterThrowing
```java
@AfterThrowing(value = "pt()", throwing = "ex")
public void afterThrowing(Exception ex) {
    System.out.println("方法抛出异常: " + ex.getMessage());
}
```

**作用：**
- 异常通知，在目标方法抛出异常时执行
- 可以获取抛出的异常信息
- 只在方法抛出异常时执行

#### @Around
```java
@Around("pt()")
public Object around(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("方法执行前");
    Object result = pjp.proceed(); // 执行目标方法
    System.out.println("方法执行后");
    return result;
}
```

**作用：**
- 环绕通知，可以完全控制目标方法的执行
- 可以修改参数、返回值，甚至不执行目标方法
- 功能最强大，但使用最复杂

### 3. 核心类及实现原理

#### 代理机制

**JDK动态代理：**
```java
// 当目标类实现接口时，Spring使用JDK动态代理
public interface UserDao {
    void save();
}

// 生成的代理类名：com.sun.proxy.$Proxy0
```

**CGLIB动态代理：**
```java
// 当目标类没有实现接口时，Spring使用CGLIB动态代理
@Component
public class UserDao {
    public void save() {
        System.out.println("save..");
    }
}

// 生成的代理类名：com.kg2s.UserDao$$EnhancerBySpringCGLIB$$xxxx
```

#### 代理类生成原理

**JDK动态代理实现：**
```java
// Spring内部实现类似以下代码
public class JdkProxy implements InvocationHandler {
    private Object target;
    
    public JdkProxy(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 执行前置通知
        beforeAdvice();
        
        // 执行目标方法
        Object result = method.invoke(target, args);
        
        // 执行后置通知
        afterAdvice();
        
        return result;
    }
}
```

**CGLIB动态代理实现：**
```java
// CGLIB通过继承目标类生成子类
public class UserDao$$EnhancerBySpringCGLIB$$xxxx extends UserDao {
    @Override
    public void save() {
        // 执行前置通知
        beforeAdvice();
        
        // 调用父类方法
        super.save();
        
        // 执行后置通知
        afterAdvice();
    }
}
```

### 4. 切点表达式详解

#### 常用切点表达式

**方法执行切点：**
```java
@Pointcut("execution(* com.kg2s.*.*(..))")
private void allMethods() {}

@Pointcut("execution(void com.kg2s.UserDao.save())")
private void saveMethod() {}

@Pointcut("execution(* com.kg2s.service.*Service.*(..))")
private void serviceMethods() {}
```

**注解切点：**
```java
@Pointcut("@annotation(com.kg2s.annotation.Log)")
private void logMethods() {}

@Pointcut("@within(org.springframework.stereotype.Service)")
private void serviceClasses() {}
```

**参数切点：**
```java
@Pointcut("args(String, int)")
private void stringIntArgs() {}

@Pointcut("args(String)")
private void stringArgs() {}
```

### 5. AOP应用场景

#### 日志记录
```java
@Aspect
@Component
public class LogAspect {
    @Around("execution(* com.kg2s.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = pjp.proceed();
            long endTime = System.currentTimeMillis();
            System.out.println(methodName + " 执行时间: " + (endTime - startTime) + "ms");
            return result;
        } catch (Exception e) {
            System.out.println(methodName + " 执行异常: " + e.getMessage());
            throw e;
        }
    }
}
```

#### 事务管理
```java
@Aspect
@Component
public class TransactionAspect {
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object transactionAround(ProceedingJoinPoint pjp) throws Throwable {
        // 开启事务
        beginTransaction();
        
        try {
            Object result = pjp.proceed();
            // 提交事务
            commitTransaction();
            return result;
        } catch (Exception e) {
            // 回滚事务
            rollbackTransaction();
            throw e;
        }
    }
}
```

#### 权限检查
```java
@Aspect
@Component
public class SecurityAspect {
    @Before("@annotation(com.kg2s.annotation.RequirePermission)")
    public void checkPermission(JoinPoint jp) {
        // 检查用户权限
        if (!hasPermission()) {
            throw new SecurityException("权限不足");
        }
    }
}
```

## 运行示例

```java
@Configuration
@ComponentScan("com.kg2s")
@EnableAspectJAutoProxy
public class SpringConfig {
    // 启用AOP功能
}

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        
        UserDao dao = context.getBean(UserDao.class);
        
        // 注意：这里获取的是代理对象
        System.out.println(dao.getClass()); // 输出代理类名
        
        dao.save(); // 会执行切面逻辑
    }
}
```

## 代理对象的特点

### 1. 代理对象类型
```java
// 有接口：com.sun.proxy.$Proxy0
// 无接口：com.kg2s.UserDao$$EnhancerBySpringCGLIB$$xxxx
```

### 2. 代理对象行为
- 代理对象实现了目标接口或继承了目标类
- 调用代理对象的方法会先执行切面逻辑
- 代理对象的方法调用会触发AOP通知

### 3. 获取真实对象
```java
// 如果需要获取真实对象（不推荐）
if (AopUtils.isAopProxy(dao)) {
    Object target = AopContext.currentProxy();
    // 或者使用其他方式获取目标对象
}
```

## 最佳实践

### 1. 合理使用切点表达式
```java
// 推荐：使用具体的包路径和方法名
@Pointcut("execution(* com.kg2s.service.UserService.*(..))")

// 不推荐：过于宽泛的表达式
@Pointcut("execution(* *.*(..))")
```

### 2. 避免在切面中修改业务逻辑
```java
// 推荐：只做横切关注点
@Before("serviceMethods()")
public void logBefore() {
    System.out.println("方法执行前");
}

// 不推荐：在切面中修改业务逻辑
@Around("serviceMethods()")
public Object modifyBusinessLogic(ProceedingJoinPoint pjp) throws Throwable {
    // 修改业务逻辑
    return pjp.proceed();
}
```

### 3. 合理选择通知类型
```java
// 简单日志：使用@Before或@After
@Before("logMethods()")
public void logBefore() {
    // 简单的前置日志
}

// 复杂逻辑：使用@Around
@Around("transactionMethods()")
public Object transactionAround(ProceedingJoinPoint pjp) throws Throwable {
    // 复杂的事务管理逻辑
}
```

## 学习要点

1. **理解AOP的核心概念**：切点、通知、切面、连接点
2. **掌握各种通知类型**：@Before、@After、@Around等
3. **了解代理机制**：JDK动态代理 vs CGLIB动态代理
4. **掌握切点表达式**：execution、annotation等表达式
5. **理解AOP的应用场景**：日志、事务、权限等

---

**相关链接：**
- [主项目README](../README.md)
- [03-Bean生命周期模块](../03-bean-lifecycle/README.md)
- [05-注解配置模块](../05-annotation-config/README.md)
