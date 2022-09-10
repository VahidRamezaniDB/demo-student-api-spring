package com.example.student.service;

import com.example.student.RunnableExample;
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
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EntityManager entityManager;
    private final SchoolRepository schoolRepository;
    private final GeneralMapper mapper;
    private final BasicLogService logger;
    private final RedissonClient redissonClient;
    public StudentService(StudentRepository studentRepository, EntityManager entityManager,
                          SchoolRepository schoolRepository, GeneralMapper mapper,
                          BasicLogService logger, RedissonClient redissonClient) {
        this.studentRepository = studentRepository;
        this.entityManager = entityManager;
        this.schoolRepository = schoolRepository;
        this.mapper = mapper;
        this.logger = logger;
        this.redissonClient = redissonClient;
    }

    public List<Student> getAllStudents(){
        List<Student> students;
        try{
            students = studentRepository.findAll();
        }catch (Exception e){
            throw new InternalServerException();
        }

        if(students.size() == 0){
            throw new NoContentException();
        }

        try {
            logger.basicLog("Selection of all students occurred.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return students;
    }


    public Student getStudentById(long id){
        Student student;
        try{
            student = studentRepository.getStudentById(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(student == null){
            throw new StudentNotFoundException();
        }

        try {
            logger.basicLog("Selection of a student occurred.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return student;
    }

    @Cacheable(value = "students", key = "#name")
    public List<Student> getStudentByName(String name){
        List<Student> students;
        try{
            students = studentRepository.getStudentByNameContains(name);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(students == null || students.size() == 0){
            throw new StudentNotFoundException();
        }

        new Thread(new RunnableExample()).start();
        System.out.println("Get student with name: " + name);
        return students;
    }

    @CachePut(value = "students", key = "#student.id")
    public Student registerStudent(Student student){
        try{
            return studentRepository.save(
                    new Student(student.getName(), student.getAge(),student.getGrade(), student.getSchool()));
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @CachePut(value = "students", key = "#id")
    public Student updateStudent(long id, Student student){
        try {
            Optional<Student> optionalStudent = studentRepository.findById(id);
            if (optionalStudent.isPresent()) {
                Student student1 = optionalStudent.get();
                student1.setName(student.getName());
                student1.setAge(student.getAge());
                student1.setGrade(student.getGrade());
                return studentRepository.save(student1);
            }else{
                throw new StudentNotFoundException();
            }
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @CacheEvict(value = "students",allEntries = true)
    public Student deleteStudentById(long id){
        try{
            Student student = studentRepository.getStudentById(id);
            if(student == null){
                throw new StudentNotFoundException();
            }
            studentRepository.deleteById(id);
            return student;
        }catch (StudentNotFoundException e){
            throw new StudentNotFoundException();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    public School getStudentSchool(long id){
        Student student;
        try{
            student = studentRepository.getStudentById(id);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(student == null){
            throw new StudentNotFoundException();
        }
        return student.getSchool();
    }

    public List<Student> getClassmates(long id){
        List<Student> students;
        try{
            studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
            students = studentRepository.getClassmates(id);
        }catch (StudentNotFoundException e){
            throw new StudentNotFoundException();
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(students == null || students.size() == 0){
            throw new StudentNotFoundException();
        }
        return students;
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
        return studentStream.
                map(student -> mapper.topStudentDtoConvertor(student.getName(),student.getSchool()))
                .collect(Collectors.toList());

    }

    public Map<String, List<Student>> getTopStudentsMap(){

        RMap<String, List<Student>> rMap = redissonClient.getMap("topStudentsMap");

        Map<String, List<Student>> map = studentRepository.getAllStudents().stream().filter(student -> student.getGrade() > 15)
                .map(Student::getSchool)
                .collect(Collectors.toMap(School::getName,
                        school -> schoolRepository.getSchoolWithStudentsByID(school.getId()).getStudents(),
                        (existing, replacement) -> existing));

        rMap.putAll(map);
        return map;
    }

}
