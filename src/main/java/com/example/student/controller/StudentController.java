package com.example.student.controller;

import com.example.student.exception.AccessForbiddenException;
import com.example.student.exception.InternalServerException;
import com.example.student.exception.NoContentException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/all")
    public List<Student> students(@RequestParam(value = "limit", defaultValue = "10") int limit,
                              @RequestHeader HttpHeaders headers){

        if(headers.get("Auth") == null){
            throw new AccessForbiddenException();
        }

        List<Student> students;
        try{
            students = studentRepository.findAll();
        }catch (Exception e){
            throw new InternalServerException();
        }

        if(students.size() == 0){
            throw new NoContentException();
        }
        return students;
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable long id, HttpServletResponse response){
        Student _student;
        try{
            _student = studentRepository.getStudentById(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(_student == null){
            throw new StudentNotFoundException();
        }
        return _student;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody Student student){

        try{
            Student _student = studentRepository.save(
                    new Student(student.getName(), student.getAge(),student.getGrade()));
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(){
        if(studentRepository.count()>0){
            return new ResponseEntity<>("Can not change real data.", HttpStatus.BAD_REQUEST);
        }
        studentRepository.save(new Student("Mike",20,15.5));
        studentRepository.save(new Student("Jeffery",18,18.5));
        studentRepository.save(new Student("Alison",19,11.75));
        studentRepository.save(new Student("Scott",22,14));
        studentRepository.save(new Student("Bruce",21,20));
        return new ResponseEntity<>("Successfuly generated new data.", HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public Student update(@PathVariable long id, @RequestBody Student data){

        Student _student;

        try{
            _student = studentRepository.updateStudentById(id, data.getName(),data.getAge(),data.getGrade());
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(_student == null){
            throw new StudentNotFoundException();
        }
        return _student;
//
//        if(id>stus.size()){
//            throw new StudentNotFoundException();
//        }
//        id--;
//
//        stus.remove((int)id);
//        stus.add((int)id, data);
//        return stus.get((int)id);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Student> delete(@PathVariable long id){
        Student _student;
        try{
            _student = studentRepository.getStudentById(id);
            if(_student == null){
                HttpHeaders header = new HttpHeaders();
                header.add("message","No data with given id.");
                return new ResponseEntity<>(null, header, HttpStatus.NOT_FOUND);
            }
            studentRepository.deleteById(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
        return new ResponseEntity<>(_student, HttpStatus.OK);
    }

}
