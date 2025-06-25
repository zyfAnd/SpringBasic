package com.kg2s.service.impl;

import com.kg2s.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    public UserServiceImpl() {
        System.out.println("UserServiceImpl : 构造方法被调用  初始化");
    }
    @Override
    public void saveUser(String name, String gender) {
        System.out.println("UserServiceImpl : saveUser " + name + " : " + gender);
    }

    @Override
    public String getUser(Long userId) {
        System.out.println("UserServiceImpl : getUser " + userId);
        return "";
    }

    @Override
    public void deleteUser(Long userId) {
        System.out.println("UserServiceImpl : deleteUser " + userId);
    }
}
