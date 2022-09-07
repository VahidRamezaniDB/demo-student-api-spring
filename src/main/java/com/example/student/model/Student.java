package com.example.student.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "grade")
    private double grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
//    @JsonIgnoreProperties("students")
    @JsonBackReference
    private School school;

    public Student(String name, int age, double grade, School school) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.school = school;
    }

    public Student() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGrade() {
        return grade;
    }

    public School getSchool() {
        return school;
    }

    @Override
    public String toString() {
        String str = "";
        str += "ID: " + this.getId() + "\n";
        str += "Name: " + this.getName() + "\n";
        str += "Age: " + this.getAge() + "\n";
        str += "Grade: " + this.getGrade();
        return str;
    }
}
