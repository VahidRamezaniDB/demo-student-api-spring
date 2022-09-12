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

    @Query(value = "select CAST(st_AsText(location) as VARCHAR) from school s where s.id=:id", nativeQuery = true)
    String getLocationById(long id);

    @Query(value = "select st_distance(s1.location, s2.location) from school s1, school s2 where s1.id=:firstId AND s2.id=:secondId"
            ,nativeQuery = true)
    double getDistanceBetween2Schools(long firstId, long secondId);
}
