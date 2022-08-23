package com.example.student.repository;

import com.example.student.model.School;
import com.example.student.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    List<School> findByName(String name);

    @EntityGraph(value = "School.students")
    School getSchoolById(long id);

    Student findTopStudent(long id);

    @Query("select s from School s join fetch s.students where s.id = ?1")
    School getSchoolWithStudentsByID(long id);

}
