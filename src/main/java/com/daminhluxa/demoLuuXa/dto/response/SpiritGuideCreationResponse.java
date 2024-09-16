package com.daminhluxa.demoLuuXa.dto.response;

import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import com.daminhluxa.demoLuuXa.enums.SpiritRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpiritGuideCreationResponse {
    String id;
    String firstName;
    String lastName;
    SpiritRole spiritRole;
    ContactInfo contactInfo;
    DormitoryShorterResponse dormitory;
}
