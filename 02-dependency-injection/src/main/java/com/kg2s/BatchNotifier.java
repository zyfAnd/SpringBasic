package com.kg2s;

import com.kg2s.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//Spring 可以注入 list set map 这类的集合 把所有实现的接口的bean 都注入进来
@Component
public class BatchNotifier {
    @Autowired
    private List<MessageService> messageServiceList;
    //通过setter 方式注入集合形式的 beans
    public void setMessageServiceList(List<MessageService> messageServiceList) {
        System.out.println("BatchNotifier  message List 集合注入 通过setter 方法");
        this.messageServiceList = messageServiceList;
    }
//    public BatchNotifier(List<MessageService> messageServiceList) {
//        System.out.println("BatchNotifier: 通过有参构造器注入messageServiceList");
//        this.messageServiceList = messageServiceList;
//    }
    public void notifyUsers(){
        System.out.println("BatchNotifier: notifyUsers - "+messageServiceList.size());
        for (MessageService messageService:messageServiceList){
            System.out.println("BatchNotifier: messageServiceList  - ");
            System.out.println(messageService.getMessage());
        }
    }
}

