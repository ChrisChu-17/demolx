package com.daminhluxa.demoLuuXa.dto.student;

import com.daminhluxa.demoLuuXa.entity.Address;
import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentUpdateRequest {
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
