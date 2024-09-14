package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.dto.guide.SpiritGuideCreationRequest;
import com.daminhluxa.demoLuuXa.dto.guide.SpiritGuideUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideCreationResponse;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideShorterResponse;
import com.daminhluxa.demoLuuXa.entity.Dormitory;
import com.daminhluxa.demoLuuXa.entity.SpiritualGuide;
import com.daminhluxa.demoLuuXa.enums.SpiritRole;
import com.daminhluxa.demoLuuXa.exception.AppException;
import com.daminhluxa.demoLuuXa.exception.ErrorCode;
import com.daminhluxa.demoLuuXa.mapper.SpiritualGuideMapper;
import com.daminhluxa.demoLuuXa.repository.DormRepository;
import com.daminhluxa.demoLuuXa.repository.SpiritualGuideRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
@Slf4j
public class SpiritualGuideService {
    SpiritualGuideRepository spiritualGuideRepository;
    SpiritualGuideMapper spiritualGuideMapper;
    private final DormRepository dormRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public SpiritGuideShorterResponse addSpiritGuide(SpiritGuideCreationRequest request) {
        SpiritualGuide guide = spiritualGuideMapper.toSpirit(request);
        SpiritRole role = SpiritRole.valueOf(request.getSpiritRole().toUpperCase());
        guide.setRole(role);
        guide = spiritualGuideRepository.save(guide);
        return spiritualGuideMapper.toShorterResponse(guide);
    }

    public SpiritGuideShorterResponse getSpiritGuide(String id) {
        return spiritualGuideMapper.toShorterResponse(spiritualGuideRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public SpiritGuideCreationResponse updateSpiritGuide(SpiritGuideUpdateRequest request, String id) {
        SpiritualGuide updateGuide = spiritualGuideRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        spiritualGuideMapper.updateGuide(updateGuide, request);
        return spiritualGuideMapper.toCreationResponse(spiritualGuideRepository.save(updateGuide));
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<SpiritGuideCreationResponse> listSpiritGuide() {
        var list = spiritualGuideRepository.findAll();
        return  list.stream().map(spiritualGuideMapper::toCreationResponse).toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSpiritGuide(String id) {
        SpiritualGuide guide = spiritualGuideRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Dormitory dorm = guide.getDormitory();
        if(dorm != null) {
            dorm.setSpiritualGuide(null);
            dormRepository.save(dorm);
        }
        spiritualGuideRepository.delete(guide);
    }
}
