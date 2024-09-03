package com.daminhluxa.demoLuuXa.mapper;

import com.daminhluxa.demoLuuXa.dto.PermissionRequest;
import com.daminhluxa.demoLuuXa.dto.UserCreationRequest;
import com.daminhluxa.demoLuuXa.dto.UserUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.PermissionResponse;
import com.daminhluxa.demoLuuXa.dto.response.UserResponse;
import com.daminhluxa.demoLuuXa.entity.Permission;
import com.daminhluxa.demoLuuXa.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
