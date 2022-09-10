package com.example.student.service;

import com.example.student.configuration.MinioConfiguration;
import com.example.student.model.Student;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class MinioService {

    private MinioClient minioClient;
    private MinioConfiguration minioConfiguration;

    public MinioService(MinioClient minioClient, MinioConfiguration minioConfiguration) {
        this.minioClient = minioClient;
        this.minioConfiguration = minioConfiguration;
    }

    public void uploadToMinio(){
        try{
            File temp = new File(minioConfiguration.getFileName());
            InputStream fileStream = new FileInputStream(temp);
            minioClient.putObject(PutObjectArgs.builder().bucket(minioConfiguration.getBucketName())
                    .object(minioConfiguration.getFileName()).stream(fileStream, temp.length(),-1).build());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void DownloadFromMinio(){
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                            .bucket(minioConfiguration.getBucketName())
                            .object(minioConfiguration.getFileName())
                            .build());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        File targetFile = new File("downloaded_pic.jpg");
        try {
            Files.copy(stream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            IOUtils.closeQuietly(stream);
        }
    }
}
