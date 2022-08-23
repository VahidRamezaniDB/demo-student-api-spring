package com.example.student.dto;

import com.example.student.model.School;
import com.example.student.model.Student;

import java.util.List;

public class TopStudentDTO {

    private String studentName;
    private String schoolName;
    private List<Student> students;

    private TopStudentDTO(String studentName, String schoolName, List<Student> students) {
        this.studentName = studentName;
        this.schoolName = schoolName;
        this.students = students;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public static TopStudentDTO convertToDto(String studentName, School school){
        return new TopStudentDTO(studentName,school.getName(), school.getStudents());
    }
}
