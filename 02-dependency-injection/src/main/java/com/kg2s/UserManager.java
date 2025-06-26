package com.kg2s;

import com.kg2s.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManager {
    @Autowired
    private MessageService messageService;

    //setter 注入 service （bean）
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendMessage() {

        System.out.println("Welcome ,"+messageService.getMessage());
    }
}
