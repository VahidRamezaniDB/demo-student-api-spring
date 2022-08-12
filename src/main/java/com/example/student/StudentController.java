package com.example.student;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class StudentController {

    private LinkedList<Student> stus = new LinkedList<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/students")
    public Student[] students(@RequestParam(value = "limit", defaultValue = "10") int limit){
        int stuCount = stus.size();
        if (limit > stuCount){
            limit = stuCount;
        }else if( limit < 1){
            limit = 1;
        }
        Student[] ret = new Student[limit];
        for (int i = 0; i < limit; i++){
            ret[i] = stus.get(i);
        }
        return ret;
    }

    @GetMapping("/student")
    public Student get(@RequestParam long id){
        id--;   
        return stus.get((int)id);
    }

    @PostMapping("/register-student")
    public Student register(@RequestBody ObjectNode data){
        Student stu = new Student(counter.incrementAndGet(),
                data.get("name").asText(),
                data.get("age").asInt(),
                data.get("grade").asDouble());
        this.stus.add(stu);
        return stu;
    }

    @PutMapping("/update-student")
    public Student update(@RequestBody ObjectNode data){
        long id = data.get("id").asLong();
        id--;
        Student currStu = stus.get((int)id);
        String name = currStu.getName();
        int age = currStu.getAge();
        double grade = currStu.getGrade();
        if( data.has("name")){
            name = data.get("name").asText();
        }if( data.has("age")){
            age = data.get("age").asInt();
        }if( data.has("grade")){
            grade = data.get("grade").asDouble();
        }
        Student stu = new Student(id+1,name,age,grade);
        stus.remove((int)id);
        stus.add((int)id, stu);
        return stus.get((int)id);
    }

    @DeleteMapping("/delete-student")
    public Student delete(@RequestParam long id){
        id--;
        Student stu = stus.get((int)id);
        stus.remove((int)id);
        return stu;
    }

}
