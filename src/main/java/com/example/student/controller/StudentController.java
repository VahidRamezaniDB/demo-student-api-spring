package com.example.student.controller;

import com.example.student.exception.AccessForbiddenException;
import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> students(@RequestHeader HttpHeaders headers){
        if(headers.get("Auth") == null){
            throw new AccessForbiddenException();
        }
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable long id){
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<List<Student>> getByName(@PathVariable String name){
        return new ResponseEntity<>(studentService.getStudentByName(name), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody Student student){
        return new ResponseEntity<>(studentService.registerStudent(student), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Student> update(@PathVariable long id, @RequestBody Student data){
        return new ResponseEntity<>(studentService.updateStudent(id,data), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Student> delete(@PathVariable long id){
        return new ResponseEntity<>(studentService.deleteStudentById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/school")
    public ResponseEntity<School> getSchool(@PathVariable long id){
        return new ResponseEntity<>(studentService.getStudentSchool(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/classmates")
    public ResponseEntity<List<Student>> getClassmates(@PathVariable long id){
        return new ResponseEntity<>(studentService.getClassmates(id), HttpStatus.OK);
    }

}
