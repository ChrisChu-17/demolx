package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.dto.MajorCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.MajorResponse;
import com.daminhluxa.demoLuuXa.entity.Major;
import com.daminhluxa.demoLuuXa.mapper.MajorMapper;
import com.daminhluxa.demoLuuXa.repository.MajorRepository;
import com.daminhluxa.demoLuuXa.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
public class MajorService {
    MajorRepository majorRepository;
    MajorMapper majorMapper;

    public MajorResponse createMajor(MajorCreationRequest request) {
        Major major = majorMapper.toMajor(request);
        majorRepository.save(major);
        return majorMapper.toMajorResponse(major);
    }

    public List<MajorResponse> getMajors() {
        var list = majorRepository.findAll();
        return list.stream().map(majorMapper::toMajorResponse).toList();
    }
}
