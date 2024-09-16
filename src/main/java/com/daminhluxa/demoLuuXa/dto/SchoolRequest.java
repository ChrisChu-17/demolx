package com.daminhluxa.demoLuuXa.dto;

import com.daminhluxa.demoLuuXa.entity.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchoolRequest {
    String name;
    String code;
    Address address;
}
