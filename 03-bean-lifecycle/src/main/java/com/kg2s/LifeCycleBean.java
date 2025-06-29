package com.kg2s;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LifeCycleBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,InitializingBean, DisposableBean {
    private String name;
    private String beanName;
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;


    public LifeCycleBean() {
        System.out.println("1. LifeCycleBean 构造器被调用 实例化");
    }

    public void setName(String name) {
        System.out.println("2. setter 方法被调用 设置 属性 name："+ name);
        this.name = name;
    }

    //BeanNameAware 接口方法
    @Override
    public void setBeanName(String name) {
        System.out.println("3.  BeanAware.setBeanName() - Bean 名称"+ name);
        this.beanName = name;
    }
    // BeanFactoryAware 接口的方法
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("4. BeanFactoryAware.setBeanFactory() - 设置 BeanFactory");
        this.beanFactory = beanFactory;
    }
    // ApplicationContextAware 接口方法
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("5. ApplicationContextAware.setApplicationContext() - 设置ApplicationContext ");
        this.applicationContext = applicationContext;
    }


    @PostConstruct
    public void postConstruct(){

    }
    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
