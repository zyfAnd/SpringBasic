package com.kg2s.service.impl;

import com.kg2s.service.GreetingService;

public class FormalGreetingService implements GreetingService {
    @Override
    public String greeting(String name) {
        System.out.println("FormalGreetingService greeting");
        return "Have a nice day " + name;
    }
}
