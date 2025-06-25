package com.kg2s.controller;

import com.kg2s.service.GreetingService;

public class GreetingController {
     GreetingService greetingService;
    public GreetingController(GreetingService greetingService ) {
        this.greetingService = greetingService;
        System.out.println("GreetingController construct initialization with args ");
    }

    public GreetingController() {
        System.out.println("GreetingController construct initialization");
    }
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
    public void sayHello(String name ) {
        System.out.println("sayHello .. " );
        String message =  greetingService.greeting(name);

        System.out.println("sayHello ... "  +" " + message);

    }
}
