package com.daminhluxa.demoLuuXa.dto;

import com.daminhluxa.demoLuuXa.entity.Permission;
import com.daminhluxa.demoLuuXa.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    String name;
    String description;
    Set<String> permissions;
}
