package com.example.student.dto;

import com.example.student.model.Student;

public class StudentDTO {
    private String name;
    private double grade;

    private StudentDTO(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public static StudentDTO convertToDto(Student student){
        return new StudentDTO(student.getName(), student.getGrade());
    }
}
