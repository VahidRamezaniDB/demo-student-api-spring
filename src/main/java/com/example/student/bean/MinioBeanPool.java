package com.example.student.bean;

import com.example.student.configuration.MinioConfiguration;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MinioBeanPool {

    private final MinioConfiguration minioConfiguration;

    public MinioBeanPool(MinioConfiguration minioConfiguration) {
        this.minioConfiguration = minioConfiguration;
    }

    @Bean
    public MinioClient minioClient(){
        MinioClient client = new MinioClient.Builder()
                .credentials(minioConfiguration.getAccessKey(), minioConfiguration.getAccessSecret())
                .endpoint(minioConfiguration.getEndpoint())
                .build();
        try {
            client.makeBucket(MakeBucketArgs.builder()
                            .bucket(minioConfiguration.getBucketName() + new Random().nextInt(1000))
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return client;
    }
}
