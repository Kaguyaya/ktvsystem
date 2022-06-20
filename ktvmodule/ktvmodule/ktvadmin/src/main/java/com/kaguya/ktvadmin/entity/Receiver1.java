package com.kaguya.ktvadmin.entity;

import com.kaguya.ktvadmin.pojo.KtvSong;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "lyhTest1")
public class Receiver1 {

    @RabbitHandler
    public void receiver(KtvSong msg){
        System.out.println("Test1 receiver1:"+msg);
    }
}