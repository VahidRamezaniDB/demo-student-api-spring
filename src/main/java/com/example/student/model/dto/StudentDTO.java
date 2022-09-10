package com.example.student.model.dto;

import com.example.student.model.Student;

public class StudentDTO {
    private String name;
    private double grade;

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
