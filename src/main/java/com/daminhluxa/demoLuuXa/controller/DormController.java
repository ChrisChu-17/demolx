package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.AssignSpiritualGuideRequest;
import com.daminhluxa.demoLuuXa.dto.dorm.DormitoryCreationRequest;
import com.daminhluxa.demoLuuXa.dto.dorm.DormitoryUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.DormitoryCreationResponse;
import com.daminhluxa.demoLuuXa.service.DormService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    @GetMapping
    public APIResponse<List<DormitoryCreationResponse>> findDormitory() {
        return  APIResponse.<List<DormitoryCreationResponse>>builder()
                .data(dormService.getDorms())
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
