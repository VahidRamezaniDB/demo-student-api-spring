package com.example.student.service;

import com.example.student.repository.SchoolRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    private SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }
}
