package com.daminhluxa.demoLuuXa.dto.guide;

import com.daminhluxa.demoLuuXa.entity.ContactInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpiritGuideUpdateRequest {
    String spiritRole;
    ContactInfo contactInfo;
}
