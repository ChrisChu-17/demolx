package com.daminhluxa.demoLuuXa.dto.response;

import com.daminhluxa.demoLuuXa.entity.Address;
import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    String id;
    String firstName;
    String lastName;
    LocalDate dob;
    ContactInfo contactInfo;
    String hostPhone;
    Set<MajorResponse> majors;
    int startYear;
    int endYear;
    Address address;
    Set<SchoolResponse> schools;
    DormitoryShorterResponse dormitory;
    Set<TranscriptResponse> transcripts;
    Set<RoleResponse> roles;
}
