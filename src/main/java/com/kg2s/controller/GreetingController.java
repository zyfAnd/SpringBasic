package com.kg2s.controller;

import com.kg2s.service.GreetingService;

public class GreetingController {
     GreetingService greetingService;
     //有参构造方法
    public GreetingController(GreetingService greetingService ) {
        this.greetingService = greetingService;
        System.out.println("通过有参构造器注入");
    }

    public GreetingController() {
        System.out.println("GreetingController construct initialization");
    }
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
        System.out.println("通过setter注入服务");
    }
    public void sayHello(String name ) {

        String message =  greetingService.greeting(name);
        System.out.println("sayHello ... "  +" " + message);

    }
}
