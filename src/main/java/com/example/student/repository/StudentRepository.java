package com.example.student.repository;

import com.example.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> getStudentByNameContains(String name);

    Student removeStudentById(long id);

    Student getStudentById(Long id);

}
