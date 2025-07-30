package com.kg2s.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect //开启这个类内部的AOP功能被识别
public class MyAdvice {
    //定义一个切入点
    @Pointcut("execution(void com.kg2s.UserDao.save())")
    private void pt(){

    }

    @Before("pt()")
    public void method (){
        System.out.println(System.currentTimeMillis());
    }
}
