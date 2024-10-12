package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.filter.FilterRequest;
import com.daminhluxa.demoLuuXa.dto.response.StudentResponse;
import com.daminhluxa.demoLuuXa.dto.student.StudentCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.StudentCreationResponse;
import com.daminhluxa.demoLuuXa.dto.student.StudentUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.transcript.TranscriptRequest;
import com.daminhluxa.demoLuuXa.service.CloudinaryService;
import com.daminhluxa.demoLuuXa.service.StudentService;
import com.daminhluxa.demoLuuXa.utils.SortUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    StudentService studentService;
    CloudinaryService cloudinaryService;

    @PostMapping
    public APIResponse<StudentCreationResponse> addStudent(
            @ModelAttribute StudentCreationRequest request,
            @RequestPart("file") MultipartFile file) {
        StudentCreationResponse response = studentService.addStudent(request, file);

        return APIResponse.<StudentCreationResponse>builder()
                .data(response)
                .build();
    }

    @PostMapping("/{studentId}/transcripts")
    public APIResponse<StudentResponse> enterTranscript(
            @PathVariable("studentId") String studentId,
            @RequestBody @Valid  TranscriptRequest request) {
        return APIResponse.<StudentResponse>builder()
                .data(studentService.enterTranscipt(studentId, request))
                .build();
    }

    @PutMapping("/{studentId}/transcripts/{transcriptId}")
    public APIResponse<StudentResponse> updateTranscript(
            @PathVariable("studentId") String studentId,
            @PathVariable("transcriptId") String transcriptId,
            @RequestBody @Valid  TranscriptRequest request) {
        return APIResponse.<StudentResponse>builder()
                .data(studentService.updateTranscript(studentId, transcriptId, request))
                .build();
    }

    @PutMapping("/{studentId}")
    public APIResponse<StudentCreationResponse> updateStudent (
            @PathVariable("studentId") String studentId,
            @RequestBody StudentUpdateRequest request) {
        return APIResponse.<StudentCreationResponse>builder()
                .data(studentService.updateStudent(request, studentId))
                .build();
    }

    @DeleteMapping("/{studentId}")
    public APIResponse<String> deleteStudent(@PathVariable("studentId") String studentId) {
        studentService.deleteStudent(studentId);
        return APIResponse.<String>builder()
                .data("Student deleted successfully")
                .build();
    }

    @GetMapping
    public APIResponse<List<StudentCreationResponse>> getStudents(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return APIResponse.<List<StudentCreationResponse>>builder()
                .data(studentService.getAllStudents(pageable))
                .build();
    }

    @GetMapping("/{studentId}")
    public APIResponse<StudentCreationResponse> getStudent(@PathVariable("studentId") String studentId) {
        return APIResponse.<StudentCreationResponse>builder()
                .data(studentService.getStudent(studentId))
                .build();
    }

    @GetMapping("/filter")
    public APIResponse<List<StudentCreationResponse>> filterStudents(
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        log.info("filterStudents: {}", filter);

        return APIResponse.<List<StudentCreationResponse>>builder()
                .data(studentService.filterStudents(filter, pageable))
                .build();
    }

    @PostMapping("/filter")
    public APIResponse<List<StudentCreationResponse>> filterStudents(
            @RequestBody FilterRequest request,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(SortUtils.parseSort(request.getSort())));
        log.info(request.toString());
        String filter = request.getFilter();
        return APIResponse.<List<StudentCreationResponse>>builder()
                .data(studentService.filterStudents(filter, pageable))
                .build();
    }

    @GetMapping("/search")
    public APIResponse<List<StudentCreationResponse>> searchStudents(
            @RequestParam String keyword,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        log.info("searchStudents: {}", keyword);
        return APIResponse.<List<StudentCreationResponse>>builder()
                .data(studentService.searchStudents(keyword, pageable))
                .build();
    }
}
