package com.kg2s.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

//定义一个servlet 容器启动类 在里面加载Spring的配置
public class ServletContainerInitConfig extends AbstractDispatcherServletInitializer {
    //加载SpringMVC容器配置 --》 tomcat 启动的时候加载这个
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext ctx  = new AnnotationConfigWebApplicationContext();
        //注册我们的springmvc 配置
        ctx.register(SpringConfig.class);
        return ctx;
    }

    //设置哪些请求归属于 spring mvc 来处理
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};//所有的请求都让spring mvc 来处理
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
    
    // 重写servlet名称，避免冲突
    @Override
    protected String getServletName() {
        return "springMvcDispatcher";
    }
}
