package com.example.student.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitConfiguration {

    private String host;
    private String virtualHost;
    private Integer port;
    private String username;
    private String password;
    private final String queueName;


    public RabbitConfiguration(String host, String virtualHost, Integer port, String username, String password) {
        this.host = host;
        this.virtualHost = virtualHost;
        this.port = port;
        this.username = username;
        this.password = password;
        this.queueName = "myQueue";
    }

    public String getHost() {
        return host;
    }

    public String getVirtualHost() {
        return virtualHost;
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

    public String getQueueName() {
        return queueName;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
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
    public Queue queue(){
        return new Queue(queueName, false);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter
    , Queue queue){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setDefaultReceiveQueue(queueName);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        rabbitTemplate.setReplyAddress(queue.getName());
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

}
