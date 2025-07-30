package com.kg2s;

import org.springframework.stereotype.Component;

@Component
public class UserDao {
    public void save(){

        System.out.println("save..");
    }
    public void update(){
        System.out.println("update..");
    }
}
