package com.kg2s.contorller;

import com.kg2s.service.GreetingService;

public class GreetingController {
    private GreetingService greetingService;


    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
        System.out.println("setter 注入service");
    }
    public GreetingController(){
        System.out.println("无参构造方法 初始化");
    }
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
        System.out.println("通过有参数的构造方法注入Service");
    }
    public void sayHello(String name) {
        System.out.println(greetingService.greeting(name) );
    }
}
