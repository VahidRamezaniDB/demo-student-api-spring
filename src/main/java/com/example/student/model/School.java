package com.example.student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "school")
//    @JsonIgnoreProperties("school")
    @JsonManagedReference
    private List<Student> students;

    @OneToOne(mappedBy = "school")
    @JsonIgnoreProperties("school")
    private Manager manager;

    public School(String name, String address, List<Student> students, Manager manager) {
        this.name = name;
        this.address = address;
        this.students = students;
        this.manager = manager;
    }

    public School() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Manager getManager() {
        return manager;
    }
}
