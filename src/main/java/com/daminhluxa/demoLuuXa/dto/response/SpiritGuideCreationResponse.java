package com.daminhluxa.demoLuuXa.dto;

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
public class SpiritGuideCreationRequest {
    String firstName;
    String lastName;
    String spiritRole;
    ContactInfo contactInfo;
}
