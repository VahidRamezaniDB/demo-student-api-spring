package com.example.student;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Queue;

@Component
public class MessageBroker implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        RabbitTemplate rabbitTemplate = new
    }

    @Bean
    public Queue myQueue(){
        return new Queue("myQueue", false);
    }
}
