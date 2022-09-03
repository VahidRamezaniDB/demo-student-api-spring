package com.example.student.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "self")
public class SelfCallConfiguration {

    private String studentUrl;

    public SelfCallConfiguration(String studentUrl) {
        this.studentUrl = studentUrl;
    }

    public String getStudentUrl() {
        return studentUrl;
    }

    public void setStudentUrl(String studentUrl) {
        this.studentUrl = studentUrl;
    }
}
