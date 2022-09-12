package com.example.student;

import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.service.*;
import com.example.student.utils.GeometryShapesUtil;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class CustomRunner implements CommandLineRunner {

    private final StudentService studentService;
    private final SchoolService schoolService;
    private final SelfService selfService;

    public CustomRunner(StudentService studentService, SchoolService schoolService
            ,SelfService selfService) {
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

    @PostConstruct
    public void linesAndPolygons(){
        LineString line1 = (LineString) GeometryShapesUtil.createLine(0,0,6,6);
        LineString line2 = (LineString) GeometryShapesUtil.createLine(0,0,5,0);
        LineString line3 = (LineString) GeometryShapesUtil.createLine(0,0,3, 3);
        LineString line4 = (LineString) GeometryShapesUtil.createLine(0,2,4.5, 2);
        LineString line5 = (LineString) GeometryShapesUtil.createLine(0,2,6, 2);
        Polygon polygon1 = (Polygon) GeometryShapesUtil.createPolygon(
                new double[]{1, 2, 3, 4, 3, 2, 1},
                new double[]{2, 1, 2, 3, 5, 3, 2});
        Polygon polygon2 = (Polygon) GeometryShapesUtil.createPolygon(
                new double[]{1,1,5,5,4,4,3,3,1},
                new double[]{1,4,4,1,1,3,3,1,1});
        System.out.println(GeometryShapesUtil.doesLineCrossPolygon(line1, polygon1));
        System.out.println(GeometryShapesUtil.doesLineCrossPolygon(line2, polygon1));
        System.out.println(GeometryShapesUtil.doesLineTraversePolygon(line1, polygon1));
        System.out.println(GeometryShapesUtil.doesLineTraversePolygon(line3, polygon1));
        System.out.println(GeometryShapesUtil.doesLineTraversePolygon(line4, polygon2));
        System.out.println(GeometryShapesUtil.doesLineTraversePolygon(line5, polygon2));

    }
}
