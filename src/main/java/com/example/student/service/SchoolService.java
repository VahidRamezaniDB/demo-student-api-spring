package com.example.student.service;

import com.example.student.exception.InternalServerException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.School;
import com.example.student.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<School> getAllSchools(){
        return schoolRepository.findAll();
    }

    public List<School> getSchoolByName(String name){
        List<School> _school;
        try{
            _school = schoolRepository.findByName(name);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(_school == null || _school.size() == 0){
            throw new StudentNotFoundException();
        }
        return _school;
    }
}
