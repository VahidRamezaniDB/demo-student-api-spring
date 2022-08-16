package com.example.student.controller;

import com.example.student.dto.SchoolDTO;
import com.example.student.dto.StudentDTO;
import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.LinkedList;
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

    @GetMapping("/get/{name}")
    public ResponseEntity<List<School>> schoolByName(@PathVariable String name){
        return new ResponseEntity<>(schoolService.getSchoolByName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}/student-grade")
    public ResponseEntity<List<StudentDTO>> studentGrade(@PathVariable long id){
        List<Student> students = schoolService.getStudents(id);
        List<StudentDTO> sdto = new LinkedList<>();
        for (Student stu : students){
            sdto.add(StudentDTO.convertToDto(stu));
        }
        return new ResponseEntity<>(sdto, HttpStatus.OK);
    }

    @GetMapping("/{id}/top-student")
    public ResponseEntity<SchoolDTO> schoolTopStudent(@PathVariable long id){
        School school = schoolService.getSchoolById(id);
        SchoolDTO dto = SchoolDTO.convertToDto(school, schoolService.getTopStudent(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
