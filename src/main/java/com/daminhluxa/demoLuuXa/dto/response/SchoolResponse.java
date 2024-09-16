package com.daminhluxa.demoLuuXa.dto.response;

import com.daminhluxa.demoLuuXa.entity.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchoolResponse {
    String id;
    String name;
    String code;
    Address address;
}
