package com.example.student;

import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.service.ForeignAPIService;
import com.example.student.service.SchoolService;
import com.example.student.service.SelfService;
import com.example.student.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class CustomRunner implements CommandLineRunner {

    private StudentService studentService;
    private SchoolService schoolService;
    private SelfService selfService;
    public CustomRunner(StudentService studentService, SchoolService schoolService,
                        SelfService selfService) {
        this.studentService = studentService;
        this.schoolService = schoolService;
        this.selfService = selfService;
    }

    @Override
    public void run(String... args) throws Exception {
//        Map<String, List<Student>> myMap = studentService.getTopStudentsMap();
//        for(String key : myMap.keySet()){
//            System.out.println(key + ":");
//            for(Student student: myMap.get(key)){
//                System.out.println("\t" + "id: " + student.getId());
//                System.out.println("\t" + "name: " + student.getName());
//                System.out.println("\t" + "grade: " + student.getGrade());
//                System.out.println("\t" + "age: " + student.getAge());
//                System.out.println();
//            }
//            System.out.println();
//        }
    }

//    @PostConstruct
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

//    @PostConstruct
    public void executorService(){
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable runnableTask = () ->{
            try {
                TimeUnit.MICROSECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("***");
            System.out.println(selfService.getRandomStudentName());
            System.out.println("***");

            try {
                TimeUnit.MICROSECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        for (int i=0;i<20;i++){
            executor.execute(runnableTask);
        }
        executor.shutdown();
    }
}
