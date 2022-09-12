package com.example.student.controller;

import com.example.student.model.dto.TopStudentDTO;
import com.example.student.controller.exception.AccessForbiddenException;
import com.example.student.model.Manager;
import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}/manager")
    public ResponseEntity<Manager> getManager(@PathVariable long id){
        return new ResponseEntity<>(studentService.getManager(id), HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopStudentDTO>> getTopStudents(){
        return new ResponseEntity<>(studentService.getTopStudents(), HttpStatus.OK);
    }

    @GetMapping("/top-map")
    public ResponseEntity<Map<String, List<Student>>> getTopStudentMap(){
        return new ResponseEntity<>(studentService.getTopStudentsMap(), HttpStatus.OK);
    }
}
