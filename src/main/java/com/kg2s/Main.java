package com.kg2s;

import com.kg2s.controller.GreetingController;
import com.kg2s.service.GreetingService;
import com.kg2s.service.impl.FormalGreetingService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//        测试setter 注入服务service
        GreetingController controller = context.getBean("greetingController", GreetingController.class);
        controller.sayHello("Zhang San");
//      测试通过有参构造起注入service
        GreetingController controller2 = context.getBean("controller2", GreetingController.class);
        controller2.sayHello("Li Si");
//      测试不同的service实现
        context.getBean("controller3", GreetingController.class).sayHello("Wang wu");
//      直接获取Service Bean
        FormalGreetingService formalGreetingService = (FormalGreetingService) context.getBean("formalGreetingService");
        formalGreetingService.greeting("Li Hong");

    }
}