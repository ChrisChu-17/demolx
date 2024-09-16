package com.daminhluxa.demoLuuXa.mapper;

import com.daminhluxa.demoLuuXa.dto.SchoolRequest;
import com.daminhluxa.demoLuuXa.dto.StudentCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.SchoolResponse;
import com.daminhluxa.demoLuuXa.dto.response.StudentCreationResponse;
import com.daminhluxa.demoLuuXa.entity.School;
import com.daminhluxa.demoLuuXa.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "majors", ignore = true)
    @Mapping(target = "transcript", ignore = true)
    @Mapping(target = "roles" , ignore = true)
    @Mapping(target = "schools", ignore = true)
    @Mapping(target = "dormitory", ignore = true)
    Student toStudent(StudentCreationRequest request);

    StudentCreationResponse toStudentCreationResponse(Student student);
}
