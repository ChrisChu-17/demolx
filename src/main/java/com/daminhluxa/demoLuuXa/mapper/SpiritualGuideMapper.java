package com.daminhluxa.demoLuuXa.mapper;
import com.daminhluxa.demoLuuXa.dto.guide.SpiritGuideCreationRequest;
import com.daminhluxa.demoLuuXa.dto.guide.SpiritGuideUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideCreationResponse;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideShorterResponse;
import com.daminhluxa.demoLuuXa.entity.SpiritualGuide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpiritualGuideMapper {

    @Mapping(target = "role", ignore = true)
    SpiritualGuide toSpirit(SpiritGuideCreationRequest request);


    SpiritGuideShorterResponse toShorterResponse(SpiritualGuide spiritualGuide);

    @Mapping(target = "spiritRole", source = "role")
    SpiritGuideCreationResponse toCreationResponse(SpiritualGuide spiritualGuide);

    @Mapping(target = "dormitory", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "role", source = "spiritRole")
    void updateGuide(@MappingTarget SpiritualGuide spiritualGuide, SpiritGuideUpdateRequest request);
}
