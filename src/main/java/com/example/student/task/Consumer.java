package com.example.student.task;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer{

    @RabbitListener(queues = "myQueue")
    public void listenForText(String in){
        System.out.println("Message from myQueue: " + in);
    }
}
