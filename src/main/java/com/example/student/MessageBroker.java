package com.example.student;

import com.example.student.service.RabbitMQSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Queue;

@Component
public class MessageBroker implements CommandLineRunner {

    private RabbitMQSender rabbitMQSender;

    public MessageBroker(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    public void run(String... args) throws Exception {
        rabbitMQSender.sendText("Hello world!");
        System.out.println("The sending message task is done.");
    }
}
