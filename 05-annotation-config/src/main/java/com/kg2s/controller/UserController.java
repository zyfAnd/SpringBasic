package com.kg2s.controller;

import com.kg2s.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public UserController() {
        System.out.println("UserController : 构造方法被调用 ");
    }
    public UserController(UserService userService) {
        System.out.println("UserController : 有参构造方法被调用");
    }
    public void saveUser(String username, String gender) {
        userService.saveUser(username, gender);
    };
    public String getUsername(Long id ) {
        return userService.getUser(id);
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }


}
