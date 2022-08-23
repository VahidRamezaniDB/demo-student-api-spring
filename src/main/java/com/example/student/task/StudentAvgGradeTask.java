package com.example.student.task;

import com.example.student.repository.StudentRepository;
import org.springframework.scheduling.annotation.Scheduled;

public class StudentAvgGradeTask {

    private StudentRepository studentRepository;

    public StudentAvgGradeTask(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Scheduled(fixedDelay = 300000)
    public void logAvgGrade(){
        System.out.println("Student Average: " + studentRepository.getAvgGrade());
    }
}
