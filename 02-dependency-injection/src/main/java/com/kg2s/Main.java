package com.kg2s;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.kg2s");
        UserManager userManager = (UserManager) context.getBean("userManager");
        userManager.sendMessage();

        context.getBean(BatchNotifier.class).notifyUsers();
    }
}