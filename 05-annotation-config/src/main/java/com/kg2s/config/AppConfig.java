package com.kg2s.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

//标记为配置勒类
@Configuration
@ComponentScan(basePackages = "com.kg2s")

public class AppConfig {

    public AppConfig() {
        System.out.println("AppConfig 构造方法 初始化");
    }
}
