package com.kg2s.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

//定义一个我们的servlet 继承Abstract Dispatcher Servlet  ， tomcat 容器启动的时候 加载这个servlet
public class ServletContainerConfig extends AbstractDispatcherServletInitializer {
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext  ctx = new AnnotationConfigWebApplicationContext();
        //注册我们的SpringMVC 配置 在application context 容器里
        ctx.register(SpringMVCConfig.class);
        return ctx;
    }

    //设置哪里处理哪些请求让Spring MVC 来处理
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
