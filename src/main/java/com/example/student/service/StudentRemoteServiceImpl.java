package com.example.student.service;

import com.example.student.repository.StudentRepository;

public class StudentRemoteServiceImpl implements StudentRemoteService{

    private StudentRepository studentRepository;

    public StudentRemoteServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public String getStudentNameById(long id) {
        return studentRepository.getStudentById(id).getName();
    }
}
