package com.kg2s;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserDao dao = context.getBean(UserDao.class);
//        dao.save();

        System.out.println(dao);
        System.out.println(dao.getClass());
    }
}