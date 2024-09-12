package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.SpiritGuideCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideCreationResponse;
import com.daminhluxa.demoLuuXa.dto.response.SpiritGuideShorterResponse;
import com.daminhluxa.demoLuuXa.service.SpiritualGuideService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spiritualGuide")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpiritualGuideController {

    SpiritualGuideService spiritualGuideService;

    @PostMapping
    APIResponse<SpiritGuideShorterResponse> addSpiritualGuide(@RequestBody SpiritGuideCreationRequest request ) {
        return APIResponse.<SpiritGuideShorterResponse>builder()
                .data(spiritualGuideService.addSpiritGuide(request))
                .build();
    }

    @GetMapping
    APIResponse<List<SpiritGuideCreationResponse>> getSpiritualGuide() {
        return APIResponse.<List<SpiritGuideCreationResponse>>builder()
                .data(spiritualGuideService.listSpiritGuide())
                .build();
    }

    @DeleteMapping("/{guideId}")
    APIResponse<String> deleteSpiritualGuide(@PathVariable String guideId) {
        spiritualGuideService.deleteSpiritGuide(guideId);
        return APIResponse.<String>builder()
                .data("Guide has been deleted")
                .build();
    }
}
