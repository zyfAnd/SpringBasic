package com.kg2s;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Yanfu Zhang
 * @date 2025-08-12
 * @description
 */
@Component
public class UserService {

    public UserService() {
        System.out.println("construct....");
    }
    @PostConstruct
    public void init(){

        System.out.println("init post construct..");
    }

    @PreDestroy
    public void destory(){
        System.out.println("destroy ...");
    }
}
