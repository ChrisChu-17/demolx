package com.daminhluxa.demoLuuXa.dto;

import com.daminhluxa.demoLuuXa.entity.Address;
import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import com.daminhluxa.demoLuuXa.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DormitoryCreationRequest {
    String name;
    LocalDate dob;
    String area;
    Address address;
    ContactInfo contactInfo;
    String description;
    String spiritualGuide;
}
