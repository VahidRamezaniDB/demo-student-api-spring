package com.example.student.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(name = "School.students",
    attributeNodes = @NamedAttributeNode("students"))
@org.hibernate.annotations.NamedNativeQuery(name = "School.findTopStudent",
        query = "SELECT *  FROM student WHERE school_id = :id ORDER BY grade DESC LIMIT 1",
        resultClass = Student.class)
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

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Student> students;

    @OneToOne(mappedBy = "school")
    @JsonManagedReference
    private Manager manager;

    private Point location;

    public School(String name, String address, List<Student> students, Manager manager) {
        this.name = name;
        this.address = address;
        this.students = students;
        this.manager = manager;
    }

    public School(long id, String name, String address, List<Student> students, Manager manager, Point location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.students = students;
        this.manager = manager;
        this.location = location;
    }

    public School() {
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
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
