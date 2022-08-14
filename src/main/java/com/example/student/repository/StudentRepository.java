package com.example.student.repository;

import com.example.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.id = ?1")
    Student getStudentById(Long id);

    @Modifying
    @Query(value = "UPDATE Student s SET s.name=?2, s.age=?3, s.grade=?4 WHERE s.id = ?1",
    nativeQuery = true)
    Student updateStudentById(Long id, String name, int age, double grade);
}
