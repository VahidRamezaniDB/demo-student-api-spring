package com.example.student.service;

import com.example.student.dto.GeneralMapper;
import com.example.student.dto.TopStudentDTO;
import com.example.student.exception.InternalServerException;
import com.example.student.exception.NoContentException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Manager;
import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.repository.SchoolRepository;
import com.example.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private EntityManager entityManager;
    private SchoolRepository schoolRepository;
    private GeneralMapper mapper;
    public StudentService(StudentRepository studentRepository, EntityManager entityManager,
                          SchoolRepository schoolRepository, GeneralMapper mapper) {
        this.studentRepository = studentRepository;
        this.entityManager = entityManager;
        this.schoolRepository = schoolRepository;
        this.mapper = mapper;
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

    public Manager getManager(long id){
        studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        try{
            return studentRepository.getManager(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    public List<TopStudentDTO> getTopStudents(){
        Query query = entityManager.createQuery("select s from Student s", Student.class);
        List<Student>  students = query.getResultList();
        Stream<Student> studentStream = students.stream().filter(student -> student.getGrade() > 15);
        List<TopStudentDTO> ret = studentStream.
                map(student -> mapper.topStudentDtoConvertor(student.getName(),student.getSchool()))
                .collect(Collectors.toList());
        return ret;

    }

    public Map<String, List<Student>> getTopStudentsMap(){

//        List<School> temp = studentRepository.findAll().stream().filter(student -> student.getGrade() > 15)
//                .map(Student::getSchool).collect(Collectors.toList());
//        return temp.stream().collect(Collectors.toMap(School::getName, School::getStudents,
//                (existing, replacement) -> existing));

        return studentRepository.getAllStudents().stream().filter(student -> student.getGrade() > 15)
                .map(Student::getSchool)
                .collect(Collectors.toMap(School::getName,
                        school -> schoolRepository.getSchoolWithStudentsByID(school.getId()).getStudents(),
                        (existing, replacement) -> existing));
//
//        return studentRepository.findAll().stream().filter(student -> student.getGrade() > 15)
//                .map(Student::getSchool)
//                .collect(Collectors.toMap(School::getName, School::getStudents,
//                        (existing, replacement) -> existing));
    }
}
