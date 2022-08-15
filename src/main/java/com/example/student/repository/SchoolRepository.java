package com.example.student.repository;

import com.example.student.model.School;
import org.springframework.data.repository.CrudRepository;

public interface SchoolRepository extends CrudRepository<School, Long> {
}
