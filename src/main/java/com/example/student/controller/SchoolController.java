package com.example.student.controller;

import com.example.student.model.School;
import com.example.student.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<School>> schools(){
        return new ResponseEntity<>(schoolService.getAllSchools(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<School>> schoolByName(@PathVariable String name){
        return new ResponseEntity<>(schoolService.getSchoolByName(name), HttpStatus.OK);
    }
}
