package com.example.student.controller;

import com.example.student.model.dto.*;
import com.example.student.model.School;
import com.example.student.model.Student;
import com.example.student.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;
    private final GeneralMapper mapper;

    public SchoolController(SchoolService schoolService, GeneralMapper mapper) {

        this.schoolService = schoolService;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<School>> schools(){
        return new ResponseEntity<>(schoolService.getAllSchools(), HttpStatus.OK);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<List<School>> schoolByName(@PathVariable String name){
        return new ResponseEntity<>(schoolService.getSchoolByName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}/student-grade")
    public ResponseEntity<List<StudentDTO>> studentGrade(@PathVariable long id){
        List<Student> students = schoolService.getStudents(id);
        List<StudentDTO> sdto = new LinkedList<>();
        for (Student stu : students){
            sdto.add(mapper.student2StudentDto(stu));
        }
        return new ResponseEntity<>(sdto, HttpStatus.OK);
    }

    @GetMapping("/{id}/top-student")
    public ResponseEntity<SchoolDTO> schoolTopStudent(@PathVariable long id){
        School school = schoolService.getSchoolById(id);
        SchoolDTO dto = mapper.getSchoolDto(school, schoolService.getTopStudent(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<LocationDTO> getSchoolLocation(@PathVariable long id){
        return ResponseEntity.ok(mapper.point2LocationDto(schoolService.getSchoolLocationById(id)));
    }

    @PostMapping("/{id}/location")
    public ResponseEntity<SchoolLocDTO> setSchoolLocation(@PathVariable long id, @RequestBody LocationDTO location){
        String loc = "POINT (" + location.getLatitude() + " " + location.getLongitude() + ")";
        School school = schoolService.setLocationForSchoolById(id, loc);
        return ResponseEntity.ok(mapper.school2SchoolLocDto(school));
    }

    @GetMapping("/{id}/distance")
    public ResponseEntity<SchoolDistanceDTO> getDistanceBetweenSchools(@PathVariable long id, @RequestParam long destId){
        double distance = schoolService.getDistance(id, destId);
        return ResponseEntity.ok(mapper.getDistanceDto(schoolService.getSchoolById(id)
                ,schoolService.getSchoolById(destId)
                ,distance));
    }
}
