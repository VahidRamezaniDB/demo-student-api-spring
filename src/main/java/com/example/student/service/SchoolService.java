package com.example.student.service;

import com.example.student.exception.InternalServerException;
import com.example.student.exception.InvalidArgument;
import com.example.student.exception.NoContentException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.model.dto.SchoolDistanceDTO;
import com.example.student.repository.SchoolRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        List<School> schools;
        try{
            schools = schoolRepository.findByName(name);
        }catch (Exception e){
            throw new InternalServerException();
        }
        if(schools == null || schools.size() == 0){
            throw new StudentNotFoundException();
        }
        return schools;
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


    public School setLocationForSchoolById(long id, String loc){
        Point location;
        try {
            location = (Point) new WKTReader().read(loc);
        } catch (ParseException e) {
            throw new InvalidArgument();
        }
        School school = schoolRepository.getSchoolById(id);
        school.setLocation(location);
        schoolRepository.save(school);
        return school;
    }

    public Point getSchoolLocationById(long id){
        Point location = null;
        try {
            location = (Point) new WKTReader().read(schoolRepository.getLocationById(id));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return location;
    }

    public double getDistance(long firstId, long secondId){
        double distance = schoolRepository.getDistanceBetween2Schools(firstId,secondId);
        return distance;
    }
}
