package com.example.student.dto;

import com.example.student.model.School;
import com.example.student.model.Student;

import java.util.List;

public class TopStudentDTO {

    private String studentName;
    private String schoolName;
    private List<Student> students;

    public String getStudentName() {
        return studentName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
