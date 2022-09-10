package com.example.student.configuration;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioBeanPool {

    private MinioConfiguration minioConfiguration;

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
                            .bucket(minioConfiguration.getBucketName())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return client;
    }
}
