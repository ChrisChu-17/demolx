package com.daminhluxa.demoLuuXa.dto.response;

import com.daminhluxa.demoLuuXa.entity.Address;
import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DormitoryCreationResponse {
    String id;
    String name;
    LocalDate dob;
    String area;
    Address address;
    ContactInfo contactInfo;
    String description;
    SpiritGuideShorterResponse spiritualGuide;
}
