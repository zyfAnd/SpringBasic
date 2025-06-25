package com.kg2s.dao.impl;


import com.kg2s.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {
    public UserDaoImpl() {
        System.out.println("UserDaoImpl 构造方法被调用 初始化");
    }
    @Override
    public void saveUser(String name, String gender) {
        System.out.println("UserDaoImpl : saveUser " + name + " : " + gender);
    }

    @Override
    public String getUser(Long userId) {
        System.out.println("UserDaoImpl : getUser " + userId);
        return "";
    }

    @Override
    public void deleteUser(Long userId) {
        System.out.println("UserDaoImpl : deleteUser " + userId);
    }
}
