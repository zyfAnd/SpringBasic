package com.kg2s.service.impl;

import com.kg2s.contorller.GreetingController;
import com.kg2s.service.GreetingService;

public class SimpleGreetingService implements GreetingService {
    private String prefix ;
    public String greeting(String name) {
        return "Hello ," + prefix + " "+ name;
    }
    //setter 注入prefix
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
