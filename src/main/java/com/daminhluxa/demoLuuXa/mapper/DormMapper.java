package com.daminhluxa.demoLuuXa.mapper;
import com.daminhluxa.demoLuuXa.dto.DormitoryCreationRequest;
import com.daminhluxa.demoLuuXa.dto.SpiritGuideCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.DormitoryCreationResponse;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideShorterResponse;
import com.daminhluxa.demoLuuXa.entity.Dormitory;
import com.daminhluxa.demoLuuXa.entity.SpiritualGuide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DormMapper {

    @Mapping(target = "spiritualGuide", ignore = true)
    Dormitory toDorm(DormitoryCreationRequest request);


    DormitoryCreationResponse toDormCreationResponse(Dormitory entity);

}
