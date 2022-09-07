package com.example.student.task;

import com.example.student.service.RabbitMQSender;
import org.springframework.scheduling.annotation.Scheduled;

public class Producer{

    private RabbitMQSender rabbitMQSender;

    public Producer(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @Scheduled(fixedDelay = 30000)
    public void produceTextMessage() {
        rabbitMQSender.sendText("Hello world!");
        System.out.println("The sending message task is done.");
    }
}
