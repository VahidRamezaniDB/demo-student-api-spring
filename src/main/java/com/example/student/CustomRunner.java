package com.example.student;

import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.service.SchoolService;
import com.example.student.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
        Map<String, List<Student>> myMap = studentService.getTopStudentsMap();
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

    @PostConstruct
    public void postConstructSampleMethod(){
        School school = schoolService.getSchoolById(2);
        if(school != null) {
            Student student = studentService.registerStudent(new Student("Fredric", 19, 17.75, school));
            System.out.println("\t" + "id: " + student.getId());
            System.out.println("\t" + "name: " + student.getName());
            System.out.println("\t" + "grade: " + student.getGrade());
            System.out.println("\t" + "age: " + student.getAge());
            System.out.println();
        }else {
            System.out.println("Here");
        }
    }
}
