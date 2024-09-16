package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.dto.StudentCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.StudentCreationResponse;
import com.daminhluxa.demoLuuXa.entity.*;
import com.daminhluxa.demoLuuXa.exception.AppException;
import com.daminhluxa.demoLuuXa.exception.ErrorCode;
import com.daminhluxa.demoLuuXa.mapper.StudentMapper;
import com.daminhluxa.demoLuuXa.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
public class StudentService {
    StudentRepository studentRepository;
    StudentMapper studentMapper;
    MajorRepository majorRepository;
    RoleRepository roleRepository;
    SchoolRepository schoolRepository;
    DormRepository dormRepository;

    public StudentCreationResponse addStudent(StudentCreationRequest request) {
        Student student = studentMapper.toStudent(request);

        Set<Major> majors = request.getMajors().stream()
                .map(majorId -> majorRepository.findById(majorId)
                        .orElseThrow(() ->new AppException(ErrorCode.MAJOR_NOT_FOUND)))
                .collect(Collectors.toSet());
        student.setMajors(majors);

        Set<Role> roles = request.getRoles().stream()
                        .map(roleName -> roleRepository.findById(roleName)
                                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED)))
                        .collect(Collectors.toSet());
        student.setRoles(roles);

        Set<School> schools = request.getSchools().stream()
                .map(schoolId -> schoolRepository.findById(schoolId)
                        .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_FOUND)))
                        .collect(Collectors.toSet());
        student.setSchools(schools);

        Dormitory dormitory = dormRepository.findById(request.getDormitory())
                .orElseThrow(() -> new AppException(ErrorCode.DORM_NOT_FOUND));
        student.setDormitory(dormitory);

        return studentMapper.toStudentCreationResponse(studentRepository.save(student));
    }
}
