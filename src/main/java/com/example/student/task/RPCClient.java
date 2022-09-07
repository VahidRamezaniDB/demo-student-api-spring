package com.example.student.task;

import com.example.student.model.Student;
import com.example.student.service.RabbitMQSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RPCClient {

    private RabbitMQSender rabbitMQSender;

    public RPCClient(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @Scheduled(fixedDelay = 50000, initialDelay = 5000)
    public void actAsRPCClient(){
        int upperBond = 7;
        long id = new Random().nextInt(upperBond)+1;

        rabbitMQSender.sendRPCRequest(id);

        System.out.println("RCP client made a request.");
    }

    @RabbitListener(queues = "rcpQueueReply")
    public void getRCPResponse(Student student){
        System.out.println(student);
    }
}
