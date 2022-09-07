package com.example.student.task;

import com.example.student.model.Student;
import com.example.student.service.RabbitMQSender;
import com.example.student.service.StudentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class RCPServer {

    private StudentService studentService;
    private RabbitMQSender rabbitMQSender;

    public RCPServer(StudentService studentService, RabbitMQSender rabbitMQSender) {
        this.studentService = studentService;
        this.rabbitMQSender = rabbitMQSender;
    }


    @RabbitListener(queues = "rcpQueue")
    public void getStudentById(long id){
        Student res = studentService.getStudentById(id);
        rabbitMQSender.sendRCPResponse(res);
    }
}
