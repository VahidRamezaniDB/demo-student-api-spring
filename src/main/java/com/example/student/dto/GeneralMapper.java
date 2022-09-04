package com.example.student.dto;

import com.example.student.model.School;
import com.example.student.model.Student;
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
}
