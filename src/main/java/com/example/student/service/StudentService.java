package com.example.student.service;

import com.example.student.exception.InternalServerException;
import com.example.student.exception.NoContentException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        List<Student> _students;
        try{
            _students = studentRepository.findAll();
        }catch (Exception e){
            throw new InternalServerException();
        }

        if(_students.size() == 0){
            throw new NoContentException();
        }
        return _students;
    }

    public Student getStudentById(long id){
        Student _student;
        try{
            _student = studentRepository.getStudentById(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(_student == null){
            throw new StudentNotFoundException();
        }
        return _student;
    }

    public List<Student> getStudentByName(String name){
        List<Student> _student;
        try{
            _student = studentRepository.getStudentByNameContains(name);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(_student == null || _student.size() == 0){
            throw new StudentNotFoundException();
        }
        return _student;
    }

    public Student registerStudent(Student student){
        try{
            return studentRepository.save(
                    new Student(student.getName(), student.getAge(),student.getGrade(), student.getSchool()));
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    public Student updateStudent(long id, Student student){
        try {
            Optional<Student> _student = studentRepository.findById(id);
            if (_student.isPresent()) {
                Student __student = _student.get();
                __student.setName(student.getName());
                __student.setAge(student.getAge());
                __student.setGrade(student.getGrade());
                return studentRepository.save(__student);
            }else{
                throw new StudentNotFoundException();
            }
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    public Student deleteStudentById(long id){
        try{
            Student _student = studentRepository.getStudentById(id);
            if(_student == null){
                throw new StudentNotFoundException();
            }
            studentRepository.deleteById(id);
            return _student;
        }catch (StudentNotFoundException e){
            throw new StudentNotFoundException();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    public School getStudentSchool(long id){
        Student _student;
        try{
            _student = studentRepository.getStudentById(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(_student == null){
            throw new StudentNotFoundException();
        }
        return _student.getSchool();
    }

    public List<Student> getClassmates(long id){
        List<Student> _students;
        try{
            studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
            _students = studentRepository.getClassmates(id);
        }catch (StudentNotFoundException e){
            throw new StudentNotFoundException();
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(_students == null || _students.size() == 0){
            throw new StudentNotFoundException();
        }
        return _students;
    }
}
