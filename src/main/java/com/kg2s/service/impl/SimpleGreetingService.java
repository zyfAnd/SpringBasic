package com.kg2s.service.impl;

import com.kg2s.service.GreetingService;

public class SimpleGreetingService implements GreetingService {
    String prefix  = "Hello, ";

    public SimpleGreetingService() {
        System.out.println("SimpleGreetingService constructor");
    }
    @Override
    public String greeting(String name) {
        return prefix + " " + name;
    }
//    setter
    public void setPrefix(String prefix) {
        this.prefix = prefix;
        System.out.println("SimpleGreetingService setPrefix");
    }
}
