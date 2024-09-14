package com.daminhluxa.demoLuuXa.dto.dorm;

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
public class DormitoryUpdateRequest {
    String name;
    String area;
    Address address;
    ContactInfo contactInfo;
    String description;
}
