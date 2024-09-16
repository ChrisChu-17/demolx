package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.SchoolRequest;
import com.daminhluxa.demoLuuXa.dto.response.SchoolResponse;
import com.daminhluxa.demoLuuXa.service.SchoolService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchoolController {
    SchoolService schoolService;

    @PostMapping
    APIResponse<SchoolResponse> createSchool(@RequestBody SchoolRequest request) {
        return APIResponse.<SchoolResponse>builder()
                .data(schoolService.addSchool(request))
                .build();
    }

    @PutMapping("/{schoolId}")
    APIResponse<SchoolResponse> updateSchool(@PathVariable String schoolId, @RequestBody SchoolRequest request) {
        return APIResponse.<SchoolResponse>builder()
                .data(schoolService.updateSchool(request,schoolId))
                .build();
    }

    @GetMapping
    APIResponse<List<SchoolResponse>> getAllSchools() {
        return APIResponse.<List<SchoolResponse>>builder()
                .data(schoolService.findAllSchools())
                .build();
    }

    @GetMapping("/{schoolId}")
    APIResponse<SchoolResponse> getSchool(@PathVariable String schoolId) {
        return APIResponse.<SchoolResponse>builder()
                .data(schoolService.findSchoolById(schoolId))
                .build();
    }

}
