package com.example.student.model.dto;

import com.example.student.model.School;
import com.example.student.model.Student;

public class SchoolDTO {

    private String name;
    private String managerName;
    private String topStudentName;

    public String getName() {
        return name;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getTopStudentName() {
        return topStudentName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setTopStudentName(String topStudentName) {
        this.topStudentName = topStudentName;
    }
}
