package com.kg2s.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //定义controller --》 定义 Spring的 Bean
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "Hello Spring MVC!");
        return "hello";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", java.util.Arrays.asList("张三", "李四", "王五"));
        return "user/list";
    }
}
