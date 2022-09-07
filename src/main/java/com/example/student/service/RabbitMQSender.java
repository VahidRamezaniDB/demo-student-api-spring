package com.example.student.service;

import com.example.student.model.Student;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private AmqpTemplate rabbitTemplate;
    private Queue queue;
    private ConnectionFactory connectionFactory;
    private MessageConverter jsonMessageConverter;

    public RabbitMQSender(AmqpTemplate rabbitTemplate, Queue queue, ConnectionFactory connectionFactory,
                          MessageConverter jsonMessageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.connectionFactory = connectionFactory;
        this.jsonMessageConverter = jsonMessageConverter;
    }

    public void sendText(String message){
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }

    public void sendRPCRequest(long id){
//        RabbitTemplate rt = new RabbitTemplate(connectionFactory);
//        rt.setDefaultReceiveQueue("rcpQueue");
//        rt.setMessageConverter(jsonMessageConverter);
//        rt.setReplyAddress("rcpQueueReply");
//        rt.setUseDirectReplyToContainer(false);
//        Student result = (Student) rt.convertSendAndReceive("rpc_exchange","rcpQueue",id);
        rabbitTemplate.convertAndSend("rcpQueue", id);

    }

    public void sendRCPResponse(Student student){
        rabbitTemplate.convertAndSend("rcpQueueReply", student);
    }
}
