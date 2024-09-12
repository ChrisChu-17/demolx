package com.daminhluxa.demoLuuXa.mapper;
import com.daminhluxa.demoLuuXa.dto.MajorCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.MajorResponse;
import com.daminhluxa.demoLuuXa.entity.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MajorMapper {
    Major toMajor(MajorCreationRequest request);

    @Mapping(target = "id", source = "id")
    MajorResponse toMajorResponse(Major major);
}
