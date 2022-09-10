package com.example.student.bean;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ReddisonBeanPool {

    @Bean
    public RedissonClient redissonClient(){
        return Redisson.create();
    }

}
