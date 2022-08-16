package com.example.student.dto;

import com.example.student.model.School;
import com.example.student.model.Student;

public class SchoolDTO {

    private String name;
    private String managerName;
    private String topStudentName;

    private SchoolDTO(String name, String managerName, String topStudentName) {
        this.name = name;
        this.managerName = managerName;
        this.topStudentName = topStudentName;
    }

    public String getName() {
        return name;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getTopStudentName() {
        return topStudentName;
    }

    public static SchoolDTO convertToDto(School school, Student top){
        return new SchoolDTO(school.getName(), school.getManager().getName(), top.getName());
    }
}
