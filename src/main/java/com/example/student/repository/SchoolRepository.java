package com.example.student.repository;

import com.example.student.model.School;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @EntityGraph(attributePaths = {"students"})
    public List<School> findByName(String name);
}
