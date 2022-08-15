package com.example.student.repository;

import com.example.student.model.Manager;
import com.example.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> getStudentByNameContains(String name);

    Student getStudentById(Long id);

    @Query(value = "select * from student left join school s on s.id = student.school_id " +
            "where student.school_id = (select school_id from student where id = ?1)",
            nativeQuery = true)
    List<Student> getClassmates(Long id);

    @Query("select c.manager from Student s join s.school c where s.id = :id")
    Manager getManager(Long id);

}
