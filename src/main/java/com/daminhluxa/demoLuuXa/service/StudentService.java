package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.dto.filter.FilterRequest;
import com.daminhluxa.demoLuuXa.dto.response.StudentResponse;
import com.daminhluxa.demoLuuXa.dto.student.StudentCreationRequest;
import com.daminhluxa.demoLuuXa.dto.response.StudentCreationResponse;
import com.daminhluxa.demoLuuXa.dto.student.StudentUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.transcript.TranscriptRequest;
import com.daminhluxa.demoLuuXa.entity.*;
import com.daminhluxa.demoLuuXa.exception.AppException;
import com.daminhluxa.demoLuuXa.exception.ErrorCode;
import com.daminhluxa.demoLuuXa.mapper.StudentMapper;
import com.daminhluxa.demoLuuXa.mapper.TranscriptMapper;
import com.daminhluxa.demoLuuXa.repository.*;
import com.daminhluxa.demoLuuXa.specification.SpecificationsBuilder;
import com.daminhluxa.demoLuuXa.specification.StudentSpecificationsBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
@Slf4j
public class StudentService {
    StudentRepository studentRepository;
    StudentMapper studentMapper;
    MajorRepository majorRepository;
    RoleRepository roleRepository;
    SchoolRepository schoolRepository;
    DormRepository dormRepository;
    TranscriptRepository transcriptRepository;
    TranscriptMapper transcriptMapper;
    CloudinaryService cloudinaryService;

    @NonFinal
    static final String HEAD_OF_DORM = "HEAD_OF_DORM";

    @NonFinal
    static final String DEPUTY_OF_DORM = "DEPUTY_OF_DORM";

    @NonFinal
    static final String MANAGER_OF_DORM = "MANAGER_OF_DORM";

    public StudentCreationResponse addStudent(StudentCreationRequest request, MultipartFile file) {
        Student student = studentMapper.toStudent(request);
        log.info("vo add");

        student.setMajors(getMajorsFromRequest(request));

        Dormitory dormitory = getDormitoryFromRequest(request);
        student.setDormitory(dormitory);

        validateRolesInDormitory(request.getRoles(), dormitory);
        student.setRoles(getRolesFromRequest(request));

        student.setSchools(getSchoolsFromRequest(request));

        Student savedStudent = studentRepository.save(student);

        byte[] fileBytes = null;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] finalFileBytes = fileBytes;
        new Thread(() -> {
           if (finalFileBytes != null) {
               String fileUrl = null;
               try {
                   fileUrl = cloudinaryService.upload(finalFileBytes);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               if (fileUrl != null) {
                   savedStudent.setImagesUrl(fileUrl);
                   studentRepository.save(savedStudent);
               }
           }
        }).start();

        return studentMapper.toStudentCreationResponse(savedStudent);
    }

//    public StudentCreationResponse addStudent(StudentCreationRequest request, MultipartFile file) {
//        Student student = studentMapper.toStudent(request);
//        log.info("vo add");
//        if (file != null && !file.isEmpty()) {
//            try {
//                student.setImagesUrl(getImagesFromRequest(file));
//            } catch (IOException e) {
//                throw new AppException(ErrorCode.UPLOAD_FILE_FAILED);
//            }
//        }
//
//        student.setMajors(getMajorsFromRequest(request));
//
//        Dormitory dormitory = getDormitoryFromRequest(request);
//        student.setDormitory(dormitory);
//
//        validateRolesInDormitory(request.getRoles(), dormitory);
//        student.setRoles(getRolesFromRequest(request));
//
//        student.setSchools(getSchoolsFromRequest(request));
//
//        return studentMapper.toStudentCreationResponse(studentRepository.save(student));
//    }



    private String getImagesFromRequest(byte[] fileBytes) throws IOException {
        return cloudinaryService.upload(fileBytes);
    }


    private void validateRolesInDormitory(Set<String> roles, Dormitory dormitory) {
        Map<String, ErrorCode> roleErrorMessages = Map.of(
                HEAD_OF_DORM, ErrorCode.EXECUTIVE_BOARD_EXISTED,
                DEPUTY_OF_DORM, ErrorCode.EXECUTIVE_BOARD_EXISTED,
                MANAGER_OF_DORM, ErrorCode.EXECUTIVE_BOARD_EXISTED
        );

        roles.stream()
                .filter(roleErrorMessages::containsKey)
                .filter(role -> hasRoleInDorm(dormitory, role))
                .findFirst()
                .ifPresent(role -> {
                    throw new AppException(roleErrorMessages.get(role));
                });
    }

    private Set<Major> getMajorsFromRequest(StudentCreationRequest request) {
        return request.getMajors().stream()
                .map(majorId -> majorRepository.findById(majorId)
                        .orElseThrow(() ->new AppException(ErrorCode.MAJOR_NOT_FOUND)))
                .collect(Collectors.toSet());
    }

    private Dormitory getDormitoryFromRequest(StudentCreationRequest request) {
        return dormRepository.findById(request.getDormitory())
                .orElseThrow(() -> new AppException(ErrorCode.DORM_NOT_FOUND));
    }

    private Set<Role> getRolesFromRequest(StudentCreationRequest request) {
        return request.getRoles().stream()
                .map(roleName -> roleRepository.findById(roleName)
                        .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED)))
                .collect(Collectors.toSet());
    }

    private Set<School> getSchoolsFromRequest(StudentCreationRequest request) {
        return request.getSchools().stream()
                .map(schoolId -> schoolRepository.findById(schoolId)
                        .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_FOUND)))
                .collect(Collectors.toSet());
    }

    private boolean hasRoleInDorm(Dormitory dormitory, String role) {
        return dormitory.getStudents().stream()
                .flatMap(student -> student.getRoles().stream())
                .anyMatch(roleName -> role.equals(roleName.getName()));
    }

    public StudentCreationResponse updateStudent(StudentUpdateRequest request, String studentId) {
        Student updatedStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        studentMapper.updateStudent(updatedStudent, request);

        Set<Major> majors = request.getMajors().stream()
                .map(majorId -> majorRepository.findById(majorId)
                        .orElseThrow(() ->new AppException(ErrorCode.MAJOR_NOT_FOUND)))
                .collect(Collectors.toSet());
        updatedStudent.setMajors(majors);

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findById(roleName)
                        .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED)))
                .collect(Collectors.toSet());
        updatedStudent.setRoles(roles);

        Set<School> schools = request.getSchools().stream()
                .map(schoolId -> schoolRepository.findById(schoolId)
                        .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_FOUND)))
                .collect(Collectors.toSet());
        updatedStudent.setSchools(schools);

        Dormitory dormitory = dormRepository.findById(request.getDormitory())
                .orElseThrow(() -> new AppException(ErrorCode.DORM_NOT_FOUND));
        updatedStudent.setDormitory(dormitory);

        return studentMapper.toStudentCreationResponse(studentRepository.save(updatedStudent));
    }

    public void deleteStudent(String studentId) {
        if(!studentRepository.existsById(studentId)) {
            throw new AppException(ErrorCode.STUDENT_NOT_FOUND);
        }
        studentRepository.deleteById(studentId);
    }

    public List<StudentCreationResponse> getAllStudents(Pageable pageable) {
        var students = studentRepository.findAll(pageable);
        return students.map(studentMapper::toStudentCreationResponse).toList();
    }

    public StudentCreationResponse getStudent(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        return studentMapper.toStudentCreationResponse(student);
    }

    public StudentResponse enterTranscipt(String studentId, TranscriptRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        Transcript transcript = transcriptMapper.toTranscript(request);
        transcript.setRating(caculateRating(request.getGpa()));
        transcript.setStudent(student);

        student.getTranscripts().add(transcript);

        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public StudentResponse updateTranscript(String studentId, String transcriptId, TranscriptRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        Transcript updatedTranscript = student.getTranscripts().stream()
                .filter(transcript -> transcript.getId().equals(transcriptId))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.TRANSCRIPT_NOT_FOUND));

        transcriptMapper.updateTranscript(updatedTranscript, request);
        updatedTranscript.setRating(caculateRating(request.getGpa()));

        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    private String caculateRating(String gpa) {
        double gpaValue = Double.parseDouble(gpa);

        if(gpaValue >= 3.6) {
            return "Excellent";
        } else if (gpaValue >= 3.2) {
            return "Very Good";
        } else if (gpaValue >= 2.5) {
            return "Good";
        } else {
            return "Average";
        }
    }

    public List<StudentCreationResponse> filterStudents(String filter, Pageable pageable) {
        SpecificationsBuilder<Student> builder = new SpecificationsBuilder<>();

        String formattedFilter = filter.endsWith(",") ? filter : filter + ",";
        Pattern pattern = Pattern.compile("([\\w\\.]+?)(:|<|>|~)([^,]+),");
        Matcher matcher = pattern.matcher(formattedFilter);

        while (matcher.find()) {
            String key = matcher.group(1).trim(); // Xử lý khoảng trắng
            String operator = matcher.group(2);
            String value = matcher.group(3).trim(); // Xử
            builder.with(key, operator,value);
        }


        Specification<Student> spec = builder.build();
        Page<Student> filteredStudents = studentRepository.findAll(spec, pageable);
        return filteredStudents.map(studentMapper::toStudentCreationResponse).toList();
    }

    public List<StudentCreationResponse> searchStudents(String keyword, Pageable pageable) {
        Page<Student> searchedStudent = studentRepository.searchStudent(keyword, pageable);
        return searchedStudent.map(studentMapper::toStudentCreationResponse).toList();
    }
}
