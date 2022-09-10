package com.example.student.bean;

import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentRemoteService;
import com.example.student.service.StudentRemoteServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitBeanPool {

    @Bean
    public StudentRemoteService studentRemoteService(StudentRepository repository){
        return new StudentRemoteServiceImpl(repository);
    }

    @Bean
    public Queue queue(){
        return new Queue("myQueue");
    }

    @Bean
    public AmqpInvokerServiceExporter exporter(StudentRemoteService serviceImpl , AmqpTemplate template){
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setServiceInterface(StudentRemoteService.class);
        exporter.setService(serviceImpl);
        exporter.setAmqpTemplate(template);
        return exporter;
    }

    @Bean
    public SimpleMessageListenerContainer listener(ConnectionFactory connectionFactory,
                                                   AmqpInvokerServiceExporter exporter,
                                                   Queue queue){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setMessageListener(exporter);
        container.setQueueNames(queue.getName());
        return container;
    }

}
