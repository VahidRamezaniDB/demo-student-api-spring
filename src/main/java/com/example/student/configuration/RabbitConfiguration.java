package com.example.student.configuration;

import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentRemoteService;
import com.example.student.service.StudentRemoteServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitConfiguration {

    private String host;
    private Integer port;
    private String username;
    private String password;


    public RabbitConfiguration(String host, Integer port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
