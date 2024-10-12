package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.AssignSpiritualGuideRequest;
import com.daminhluxa.demoLuuXa.dto.dorm.DormitoryCreationRequest;
import com.daminhluxa.demoLuuXa.dto.dorm.DormitoryUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.filter.FilterRequest;
import com.daminhluxa.demoLuuXa.dto.response.DormitoryCreationResponse;
import com.daminhluxa.demoLuuXa.dto.response.StudentResponse;
import com.daminhluxa.demoLuuXa.service.DormService;
import com.daminhluxa.demoLuuXa.utils.SortUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dorm")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DormController {
    DormService dormService;

    @PostMapping
    public APIResponse<DormitoryCreationResponse> addDormitory(
            @RequestBody DormitoryCreationRequest request) {
        return APIResponse.<DormitoryCreationResponse>builder()
                .data(dormService.createDormitory(request))
                .build();
    }

    @PostMapping("/filter")
    APIResponse<List<DormitoryCreationResponse>> filterDormitory(
            @RequestBody FilterRequest request,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(SortUtils.parseSort(request.getSort())));
        String filter = request.getFilter();
        return APIResponse.<List<DormitoryCreationResponse>>builder()
                .data(dormService.filterDorm(filter,pageable))
                .build();
    }

    @PutMapping("/{dormId}/guide-assigned")
    public APIResponse<DormitoryCreationResponse> assignSpiritualGuide(
            @PathVariable("dormId") String dormId,
            @RequestBody AssignSpiritualGuideRequest request) {
        return  APIResponse.<DormitoryCreationResponse>builder()
                .data(dormService.assignSpiritualGuide(dormId, request.getSpiritualGuideId()))
                .build();
    }

    @PutMapping("/{dormId}")
    public APIResponse<DormitoryCreationResponse> updateDormitory(
            @RequestBody DormitoryUpdateRequest request,
            @PathVariable String dormId) {
        return APIResponse.<DormitoryCreationResponse>builder()
                .data(dormService.updateDormitory(request, dormId))
                .build();
    }

    @GetMapping("/search")
    public APIResponse<List<DormitoryCreationResponse>> searchDormitory(
            @RequestParam String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(SortUtils.parseSort(keyword)));
        return APIResponse.<List<DormitoryCreationResponse>>builder()
                .data(dormService.searchDorm(keyword, pageable))
                .build();
    }

    @GetMapping
    public APIResponse<List<DormitoryCreationResponse>> findDormitory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return  APIResponse.<List<DormitoryCreationResponse>>builder()
                .data(dormService.getDorms(pageable))
                .build();
    }

    @GetMapping("/{dormId}/students")
    public APIResponse<List<StudentResponse>> getAllStudentsByDorm(
            @PathVariable("dormId") String dormId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return APIResponse.<List<StudentResponse>>builder()
                .data(dormService.getAllStudentsByDorm(dormId, pageable))
                .build();
    }

    @DeleteMapping("/{dormId}")
    public APIResponse<String> deleteDormitory(@PathVariable("dormId") String dormId) {
        dormService.deleteDormitory(dormId);
        return APIResponse.<String>builder()
                .data("Dorm has been deleted")
                .build();
    }
}
