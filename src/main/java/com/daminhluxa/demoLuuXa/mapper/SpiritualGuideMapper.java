package com.daminhluxa.demoLuuXa.mapper;
import com.daminhluxa.demoLuuXa.dto.MajorCreationRequest;
import com.daminhluxa.demoLuuXa.dto.SpiritGuideCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.MajorResponse;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideCreationResponse;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideShorterResponse;
import com.daminhluxa.demoLuuXa.entity.Major;
import com.daminhluxa.demoLuuXa.entity.SpiritualGuide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpiritualGuideMapper {

    @Mapping(target = "role", ignore = true)
    SpiritualGuide toSpirit(SpiritGuideCreationRequest request);


    SpiritGuideShorterResponse toShorterResponse(SpiritualGuide spiritualGuide);

    @Mapping(target = "spiritRole", source = "role")
    SpiritGuideCreationResponse toCreationResponse(SpiritualGuide spiritualGuide);
}
