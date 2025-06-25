package com.kg2s;

import com.kg2s.controller.GreetingController;
import com.kg2s.service.GreetingService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        GreetingController controller = context.getBean("greetingController", GreetingController.class);
        controller.sayHello("Zhang San");
    }
}