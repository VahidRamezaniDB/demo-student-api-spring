package com.example.student.service;

import com.example.student.exception.InternalServerException;
import com.example.student.exception.NoContentException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.repository.SchoolRepository;
import org.hibernate.query.NativeQuery;
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

    public School getSchoolById(long id){
        School school;
        try {
            school = schoolRepository.getSchoolById(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(school == null){
            throw new StudentNotFoundException();
        }
        return school;
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

    public List<Student> getStudents(long id){
        School school;

        try {
            school = schoolRepository.getSchoolById(id);
        }catch (Exception e){throw new InternalServerException();}

        if(school == null){throw new StudentNotFoundException();}

        List<Student> students;

        try {
            students = school.getStudents();
        }catch (Exception e){throw new InternalServerException();}

        if(students == null || students.size()==0){
            throw new NoContentException();
        }
        return students;

    }

    public Student getTopStudent(long id){
        Student student;
        try{
            student = schoolRepository.findTopStudent(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
        return student;
    }
}
