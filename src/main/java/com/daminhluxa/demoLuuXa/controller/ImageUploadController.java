package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.service.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageUploadController {

    CloudinaryService cloudinaryService;

    @PostMapping
    public APIResponse<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            return APIResponse.<String>builder()
                    .data("Image uploaded successfully: " + cloudinaryService.upload(file.getBytes()))
                    .build();
        } catch (IOException e) {
            return APIResponse.<String>builder()
                    .data("Image upload failed: " + e.getMessage())
                    .build();
        }
    }
}
