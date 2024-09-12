package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.MajorCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.MajorResponse;
import com.daminhluxa.demoLuuXa.service.MajorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/major")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MajorController {
    MajorService majorService;

    @PostMapping
    public APIResponse<MajorResponse> addMajor(@RequestBody MajorCreationRequest request) {
        return APIResponse.<MajorResponse>builder()
                .data(majorService.createMajor(request))
                .build();
    }

    @GetMapping
    public APIResponse<List<MajorResponse>> getMajors() {
        return APIResponse.<List<MajorResponse>>builder()
                .data(majorService.getMajors())
                .build();
    }

}
