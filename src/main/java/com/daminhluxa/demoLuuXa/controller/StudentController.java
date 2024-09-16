package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.SchoolRequest;
import com.daminhluxa.demoLuuXa.dto.StudentCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.SchoolResponse;
import com.daminhluxa.demoLuuXa.dto.response.StudentCreationResponse;
import com.daminhluxa.demoLuuXa.service.SchoolService;
import com.daminhluxa.demoLuuXa.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {
    StudentService studentService;

    @PostMapping
    public APIResponse<StudentCreationResponse> addStudent(@RequestBody StudentCreationRequest request) {
        return APIResponse.<StudentCreationResponse>builder()
                .data(studentService.addStudent(request))
                .build();
    }

}
