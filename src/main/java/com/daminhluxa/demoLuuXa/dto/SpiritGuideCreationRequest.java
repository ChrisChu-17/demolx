package com.daminhluxa.demoLuuXa.dto;

import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
