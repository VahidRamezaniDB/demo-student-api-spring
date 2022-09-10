package com.example.student.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "minio")
public class MinioConfiguration {

    private String bucketName;
    private String accessKey;
    private String accessSecret;
    private String endpoint;
    private final String fileName;

    public MinioConfiguration(String bucketName, String accessKey, String accessSecret, String endpoint) {
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.endpoint = endpoint;
        fileName = "pic.jpg";
    }

    public String getFileName() {
        return fileName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
