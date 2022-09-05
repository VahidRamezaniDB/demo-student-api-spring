package com.example.student.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BasicLogService{

    @Async
    public void basicLog(String message) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(message);
    }
}

