package com.example.student.model.dto;

import com.example.student.model.School;
import com.example.student.model.Student;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

    @Mapping(target = "name", source = "student.name")
    @Mapping(target = "grade", source = "student.grade")
    StudentDTO student2StudentDto(Student student);

    @Mapping(target = "studentName", source = "studentName")
    @Mapping(target = "schoolName", source = "school.name")
    @Mapping(target = "students", source = "school.students")
    TopStudentDTO topStudentDtoConvertor(String studentName, School school);

    @Mapping(target = "name", source = "school.name")
    @Mapping(target = "managerName", source = "school.manager.name")
    @Mapping(target = "topStudentName", source = "top.name")
    SchoolDTO getSchoolDto(School school, Student top);

    @Mapping(target = "name", source = "school.name")
    @Mapping(target = "address", source = "school.address")
    @Mapping(target = "location", source = "school.location")
    SchoolLocDTO school2SchoolLocDto(School school);

    @Mapping(target = "latitude", source = "point.x")
    @Mapping(target = "longitude", source = "point.y")
    LocationDTO point2LocationDto(Point point);

    @Mapping(target = "school1Name", source = "firstSchool.name")
    @Mapping(target = "school2Name", source = "secondSchool.name")
    @Mapping(target = "distance", source = "dist")
    SchoolDistanceDTO getDistanceDto(School firstSchool, School secondSchool, double dist);
}
