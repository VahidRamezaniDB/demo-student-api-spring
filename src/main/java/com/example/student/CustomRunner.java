package com.example.student;

import com.example.student.model.Student;
import com.example.student.service.SchoolService;
import com.example.student.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomRunner implements CommandLineRunner {

    private StudentService studentService;
    private SchoolService schoolService;

    public CustomRunner(StudentService studentService, SchoolService schoolService) {
        this.studentService = studentService;
        this.schoolService = schoolService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Here");
        Map<String, List<Student>> myMap = studentService.getTopStudentsMap();
        System.out.println(myMap.get("high").get(0).getName());
        System.out.println("Here!!!");
        for(String key : myMap.keySet()){
            System.out.println(key + ":");
            for(Student student: myMap.get(key)){
                System.out.println("\t" + "id: " + student.getId());
                System.out.println("\t" + "name: " + student.getName());
                System.out.println("\t" + "grade: " + student.getGrade());
                System.out.println("\t" + "age: " + student.getAge());
                System.out.println();
            }
            System.out.println();
        }
    }
}
