package com.kg2s;

import com.kg2s.contorller.GreetingController;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        context.getBean("controller1",  GreetingController.class).sayHello("Zhang San");
        context.getBean("controller2",  GreetingController.class).sayHello("Li Si");
        context.getBean("controller3",GreetingController.class).sayHello("Wang Wu");
    }
}