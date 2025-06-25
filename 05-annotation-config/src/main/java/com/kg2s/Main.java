package com.kg2s;

import com.kg2s.config.AppConfig;
import com.kg2s.controller.UserController;
import com.kg2s.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.ObjectInputFilter;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        UserController controller = applicationContext.getBean(UserController.class);
        controller.getUsername(Long.valueOf(12));
    }
}