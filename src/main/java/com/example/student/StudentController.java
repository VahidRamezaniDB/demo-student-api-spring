package com.example.student;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/student")
public class StudentController {

    private LinkedList<Student> stus = new LinkedList<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/all")
    public Student[] students(@RequestParam(value = "limit", defaultValue = "10") int limit,
                              @RequestHeader HttpHeaders headers){
        if(headers.get("Auth") == null){
            throw new AccessForbiddenException();
        }
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

    @GetMapping("/{id}")
    public Student get(@PathVariable long id, HttpServletResponse response){
        if(id> stus.size()){
            response.setStatus(404);
            response.setHeader("message", "Data not found.");
            return null;
        }
        id--;
        return stus.get((int)id);
    }

    @PostMapping("/register")
    public Student register(@RequestBody ObjectNode data){
        Student stu = new Student(counter.incrementAndGet(),
                data.get("name").asText(),
                data.get("age").asInt(),
                data.get("grade").asDouble());
        this.stus.add(stu);
        return stu;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(){
        if(stus.size()>0){
            return new ResponseEntity<>("Can not change real data.", HttpStatus.BAD_REQUEST);
        }
        stus.add(new Student(counter.incrementAndGet(),"Mike",20,15.5));
        stus.add(new Student(counter.incrementAndGet(),"Jeffery",18,18.5));
        stus.add(new Student(counter.incrementAndGet(),"Alison",19,11.75));
        stus.add(new Student(counter.incrementAndGet(),"Scott",22,14));
        stus.add(new Student(counter.incrementAndGet(),"Bruce",21,20));
        return new ResponseEntity<>("Successfuly generated new data.", HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public Student update(@PathVariable long id, @RequestBody Student data){

        if(id>stus.size()){
            throw new StudentNotFoundException();
        }
        id--;

        stus.remove((int)id);
        stus.add((int)id, data);
        return stus.get((int)id);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Student> delete(@PathVariable long id){
        if(id>stus.size()){
            HttpHeaders header = new HttpHeaders();
            header.add("message","No data with given id.");
            return new ResponseEntity<>(null, header, HttpStatus.NOT_FOUND);
        }
        id--;
        Student stu = stus.get((int)id);
        stus.remove((int)id);
        return new ResponseEntity<>(stu, HttpStatus.OK);
    }

}
