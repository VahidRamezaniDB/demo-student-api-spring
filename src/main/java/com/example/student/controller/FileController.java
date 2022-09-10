package com.example.student.controller;

import com.example.student.configuration.MinioConfiguration;
import com.example.student.service.MinioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {

    private MinioService minioService;

    public FileController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping("/upload")
    public ResponseEntity<String> uploadFileToMinio(){
        minioService.uploadToMinio();
        return ResponseEntity.ok("Successful");
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadFileFromMinio(){
        minioService.DownloadFromMinio();
        return ResponseEntity.ok("Successful");
    }
}
