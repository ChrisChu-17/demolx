package com.daminhluxa.demoLuuXa.dto;

import com.daminhluxa.demoLuuXa.entity.Address;
import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import com.daminhluxa.demoLuuXa.entity.Dormitory;
import com.daminhluxa.demoLuuXa.entity.Transcript;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCreationRequest {
    String firstName;
    String lastName;
    LocalDate dob;

    ContactInfo contactInfo;
    String hostPhone;
    Set<String> majors;
    int startYear;
    int endYear;
    Address address;
    Set<String> schools;
    String dormitory;
    Set<String> roles;
}
