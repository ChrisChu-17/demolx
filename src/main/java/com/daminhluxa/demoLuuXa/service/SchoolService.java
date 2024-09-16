package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.dto.SchoolRequest;
import com.daminhluxa.demoLuuXa.dto.response.SchoolResponse;
import com.daminhluxa.demoLuuXa.entity.School;
import com.daminhluxa.demoLuuXa.exception.AppException;
import com.daminhluxa.demoLuuXa.exception.ErrorCode;
import com.daminhluxa.demoLuuXa.mapper.SchoolMapper;
import com.daminhluxa.demoLuuXa.repository.SchoolRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
public class SchoolService {
    SchoolRepository schoolRepository;
    SchoolMapper schoolMapper;

    public SchoolResponse addSchool(SchoolRequest request) {
        try {
            School school = schoolMapper.toSchool(request);
            schoolRepository.save(school);
            return schoolMapper.toSchoolResponse(school);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.SCHOOL_EXISTED);
        }
    }

    public SchoolResponse updateSchool(SchoolRequest request, String schoolId) {
        School updatedSchool = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_FOUND));

        schoolMapper.updateSchool(updatedSchool, request);
        return schoolMapper.toSchoolResponse(schoolRepository.save(updatedSchool));
    }


    public List<SchoolResponse> findAllSchools() {
        var schools = schoolRepository.findAll();
        return schools.stream().map(schoolMapper::toSchoolResponse).toList();
    }

    public SchoolResponse findSchoolById(String schoolId) {
        var school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_FOUND));
        return schoolMapper.toSchoolResponse(school);
    }
}
