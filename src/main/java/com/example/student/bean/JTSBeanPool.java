package com.example.student.bean;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JTSBeanPool {

    @Bean
    public JtsModule jtsModule(){
        return new JtsModule();
    }
}
