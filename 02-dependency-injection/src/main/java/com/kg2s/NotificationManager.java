package com.kg2s;

import com.kg2s.service.MessageService;

public class NotificationManager {
    private MessageService messageService;
    //构造器注入
    public NotificationManager(MessageService messageService) {
        System.out.println("NotificationManager ： messageService 通过有参构造器方法 注入");
        this.messageService = messageService;
    }
    public  void notifyUser() {
        System.out.println(messageService.getMessage());
    }

}
