package com.kg2s.service.impl;

import com.kg2s.service.MessageService;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage() {
        return "Thanks for your applying this job";
    }
}
