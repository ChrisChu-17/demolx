package com.daminhluxa.demoLuuXa.mapper;

import com.daminhluxa.demoLuuXa.dto.response.StudentResponse;
import com.daminhluxa.demoLuuXa.dto.student.StudentCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.StudentCreationResponse;
import com.daminhluxa.demoLuuXa.dto.student.StudentUpdateRequest;
import com.daminhluxa.demoLuuXa.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "majors", ignore = true)
    @Mapping(target = "transcripts", ignore = true)
    @Mapping(target = "roles" , ignore = true)
    @Mapping(target = "schools", ignore = true)
    @Mapping(target = "dormitory", ignore = true)
    Student toStudent(StudentCreationRequest request);

    @Mapping(target = "majors", ignore = true)
    @Mapping(target = "roles" , ignore = true)
    @Mapping(target = "schools", ignore = true)
    @Mapping(target = "dormitory", ignore = true)
    void updateStudent(@MappingTarget Student student, StudentUpdateRequest request) ;

    StudentCreationResponse toStudentCreationResponse(Student student);

    StudentResponse toStudentResponse(Student student);
}
