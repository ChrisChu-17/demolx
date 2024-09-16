package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.dto.dorm.DormitoryCreationRequest;
import com.daminhluxa.demoLuuXa.dto.dorm.DormitoryUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.DormitoryCreationResponse;
import com.daminhluxa.demoLuuXa.entity.Dormitory;
import com.daminhluxa.demoLuuXa.entity.SpiritualGuide;
import com.daminhluxa.demoLuuXa.exception.AppException;
import com.daminhluxa.demoLuuXa.exception.ErrorCode;
import com.daminhluxa.demoLuuXa.mapper.DormMapper;
import com.daminhluxa.demoLuuXa.repository.DormRepository;
import com.daminhluxa.demoLuuXa.repository.SpiritualGuideRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
@Slf4j
public class DormService {
    DormRepository dormRepository;

    DormMapper dormMapper;

    SpiritualGuideRepository spiritualGuideRepository;

    public DormitoryCreationResponse createDormitory(DormitoryCreationRequest request) {
        SpiritualGuide spiritualGuide = spiritualGuideRepository.findById(request.getSpiritualGuide())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));


        // Kiểm tra nếu spiritualGuide đã được gán dormitory
        if (spiritualGuide.getDormitory() != null &&
                spiritualGuideRepository.existsByDormitory(spiritualGuide.getDormitory())) {
            throw new AppException(ErrorCode.SPIRITUAL_GUIDE_HAS_BEEN_USED);
        }

        Dormitory dormitory = dormMapper.toDorm(request);
        dormitory.setSpiritualGuide(spiritualGuide);

        try {
            Dormitory savedDormitory = dormRepository.save(dormitory);
            spiritualGuide.setDormitory(savedDormitory);
            spiritualGuideRepository.save(spiritualGuide);
            return dormMapper.toDormCreationResponse(savedDormitory);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.DORM_EXISTED);
        }
    }

    public List<DormitoryCreationResponse> getDorms() {
        var dorms = dormRepository.findAll();
        return dorms.stream().map(dormMapper::toDormCreationResponse).toList();
    }

    public DormitoryCreationResponse getDorm(String id) {
        return dormMapper.toDormCreationResponse(dormRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public DormitoryCreationResponse updateDormitory(DormitoryUpdateRequest request, String id) {
        dormRepository.findByName(request.getName())
                .ifPresent(dormitory -> {
                   throw new AppException(ErrorCode.DORM_EXISTED);
                });

        Dormitory updatedDorm = dormRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DORM_NOT_FOUND));

        dormMapper.updateDorm(updatedDorm, request);
        return dormMapper.toDormCreationResponse(dormRepository.save(updatedDorm));
    }

    public DormitoryCreationResponse assignSpiritualGuide(String dormId, String spiritualGuideId) {
        Dormitory dorm = dormRepository.findById(dormId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        SpiritualGuide newSpiritualGuide = spiritualGuideRepository.findById(spiritualGuideId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Nếu spiritual guide mới đã được gán cho dormitory khác, gỡ bỏ quan hệ
        if(spiritualGuideRepository.existsByDormitory(newSpiritualGuide.getDormitory())) {
            Dormitory existedDorm = newSpiritualGuide.getDormitory();
            existedDorm.setSpiritualGuide(null);
            dormRepository.save(existedDorm);
        }

        // Gỡ bỏ spiritual guide hiện tại của dormitory (nếu có)
        SpiritualGuide currentSpiritualGuide = dorm.getSpiritualGuide();
        if(currentSpiritualGuide != null) {
            currentSpiritualGuide.setDormitory(null);
            spiritualGuideRepository.save(currentSpiritualGuide);
        }

        dorm.setSpiritualGuide(newSpiritualGuide);
        Dormitory savedDorm = dormRepository.save(dorm);

        newSpiritualGuide.setDormitory(savedDorm);
        spiritualGuideRepository.save(newSpiritualGuide);
        return dormMapper.toDormCreationResponse(savedDorm);
    }


    //Đang fix
    public void deleteDormitory(String dormId) {
        Dormitory dorm = dormRepository.findById(dormId)
                .orElseThrow(() -> new AppException(ErrorCode.DORM_NOT_FOUND));

        SpiritualGuide spiritualGuide = dorm.getSpiritualGuide();
        if(spiritualGuide != null) {
            spiritualGuide.setDormitory(null);
            spiritualGuideRepository.save(spiritualGuide);
        }
        dormRepository.delete(dorm);
    }
}
