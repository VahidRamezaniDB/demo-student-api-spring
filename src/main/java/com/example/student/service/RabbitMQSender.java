package com.example.student.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private AmqpTemplate rabbitTemplate;
    private Queue queue;

    public RabbitMQSender(AmqpTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendText(String message){
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }
}
