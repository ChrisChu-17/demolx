package com.daminhluxa.demoLuuXa.mapper;

import com.daminhluxa.demoLuuXa.dto.SchoolRequest;
import com.daminhluxa.demoLuuXa.dto.response.SchoolResponse;
import com.daminhluxa.demoLuuXa.entity.School;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    @Mapping(target = "code", source = "code")
    School toSchool(SchoolRequest request);

    void updateSchool(@MappingTarget School school, SchoolRequest request);

    @Mapping(target = "code", source = "code")
    SchoolResponse toSchoolResponse(School school);
}
