package com.kg2s.service.impl;

import com.kg2s.service.GreetingService;

public class FormalGreetingService implements GreetingService {
    @Override
    public String greeting(String name) {
        return "Have a nice day !  " + name;
    }


}
