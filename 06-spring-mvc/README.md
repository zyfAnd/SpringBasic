# 06-Spring MVC模块

## 模块概述
本模块深入讲解Spring MVC框架，学习Web层开发，掌握控制器、视图解析、数据绑定、拦截器等核心概念。

## 详细操作步骤

### 1. 必须的依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.23</version>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
```

### 2. 主要使用的注解

#### @Controller
```java
@Controller
public class UserController {
    // 控制器类
}
```

**作用：**
- 标识一个类为Spring MVC控制器
- 处理HTTP请求和响应
- 配合@RequestMapping使用

#### @RequestMapping
```java
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUsers(Model model) {
        // 处理GET请求 /user/list
        return "user/list";
    }
}
```

**作用：**
- 映射HTTP请求到处理方法
- 支持类级别和方法级别映射
- 可以指定请求方法、参数、头部等

#### @GetMapping / @PostMapping
```java
@GetMapping("/users")
public String getUsers(Model model) {
    // 处理GET请求
}

@PostMapping("/users")
public String createUser(@RequestBody User user) {
    // 处理POST请求
}
```

**作用：**
- 简化@RequestMapping的写法
- 分别处理GET、POST、PUT、DELETE等请求

#### @ResponseBody
```java
@GetMapping("/api/users")
@ResponseBody
public List<User> getUsers() {
    // 返回JSON数据
    return userService.findAll();
}
```

**作用：**
- 将方法返回值直接写入响应体
- 通常用于REST API
- 自动进行JSON序列化

#### @PathVariable
```java
@GetMapping("/users/{id}")
public String getUser(@PathVariable Long id, Model model) {
    User user = userService.findById(id);
    model.addAttribute("user", user);
    return "user/detail";
}
```

**作用：**
- 绑定URL路径变量
- 支持路径模板
- 可以指定参数名

#### @RequestParam
```java
@GetMapping("/users")
public String searchUsers(@RequestParam String name, 
                         @RequestParam(defaultValue = "1") int page, 
                         Model model) {
    // 处理查询参数
}
```

**作用：**
- 绑定请求参数
- 支持默认值
- 可以指定是否必需

### 3. 核心类及实现原理

#### DispatcherServlet
```java
// web.xml配置
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
```

**核心功能：**
- Spring MVC的前端控制器
- 接收所有HTTP请求
- 根据URL映射分发到对应的Controller

**处理流程：**
1. 接收HTTP请求
2. 查找HandlerMapping
3. 调用HandlerAdapter
4. 执行Controller方法
5. 返回ModelAndView
6. 视图解析和渲染

#### HandlerMapping
```java
// 默认使用RequestMappingHandlerMapping
// 根据@RequestMapping注解映射请求到方法
```

**作用：**
- 将请求URL映射到处理方法
- 支持多种映射策略
- 处理请求参数匹配

#### ViewResolver
```java
@Bean
public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
}
```

**作用：**
- 解析视图名称
- 定位视图资源
- 支持多种视图技术

### 4. 数据绑定和验证

#### @ModelAttribute
```java
@PostMapping("/users")
public String createUser(@ModelAttribute User user, BindingResult result) {
    if (result.hasErrors()) {
        return "user/form";
    }
    userService.save(user);
    return "redirect:/users";
}
```

**作用：**
- 绑定表单数据到对象
- 自动类型转换
- 支持数据验证

#### @Valid
```java
@PostMapping("/users")
public String createUser(@Valid @ModelAttribute User user, BindingResult result) {
    // 验证用户数据
}
```

**作用：**
- 启用数据验证
- 配合JSR-303注解使用
- 验证失败时填充BindingResult

### 5. 拦截器

#### HandlerInterceptor
```java
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("请求开始: " + request.getRequestURI());
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("请求处理完成");
    }
}
```

**作用：**
- 在请求处理前后执行
- 可以修改请求和响应
- 支持多个拦截器链式调用

## 运行示例

```java
@Configuration
@EnableWebMvc
@ComponentScan("com.kg2s")
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
}

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
    
    @PostMapping("/users")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
```

## 最佳实践

### 1. RESTful API设计
```java
@RestController
@RequestMapping("/api/users")
public class UserApiController {
    @GetMapping
    public List<User> getUsers() { }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) { }
    
    @PostMapping
    public User createUser(@RequestBody User user) { }
    
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) { }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) { }
}
```

### 2. 异常处理
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
```

## 学习要点

1. **理解MVC架构模式**：Model-View-Controller分离
2. **掌握请求映射**：@RequestMapping、@GetMapping等
3. **了解数据绑定**：@ModelAttribute、@RequestParam等
4. **理解视图解析**：ViewResolver机制
5. **掌握拦截器**：HandlerInterceptor使用

---

**相关链接：**
- [主项目README](../README.md)
- [05-注解配置模块](../05-annotation-config/README.md)
- [07-数据访问模块](../07-data-access/README.md) 