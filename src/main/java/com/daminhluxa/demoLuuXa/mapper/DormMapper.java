package com.daminhluxa.demoLuuXa.mapper;
import com.daminhluxa.demoLuuXa.dto.dorm.DormitoryCreationRequest;
import com.daminhluxa.demoLuuXa.dto.dorm.DormitoryUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.DormitoryCreationResponse;
import com.daminhluxa.demoLuuXa.entity.Dormitory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DormMapper {

    @Mapping(target = "spiritualGuide", ignore = true)
    Dormitory toDorm(DormitoryCreationRequest request);


    DormitoryCreationResponse toDormCreationResponse(Dormitory entity);

    @Mapping(target = "dob", ignore = true)
    @Mapping(target = "spiritualGuide", ignore = true)
    void updateDorm(@MappingTarget Dormitory entity, DormitoryUpdateRequest request);

}
