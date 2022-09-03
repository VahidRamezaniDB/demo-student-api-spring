package com.example.student.controller;

import com.example.student.service.SelfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/self")
public class SelfCallController {

    private SelfService service;

    public SelfCallController(SelfService service) {
        this.service = service;
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<String> selfCallTest(@PathVariable long id){
        service.callSelfApi(id);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }
}
