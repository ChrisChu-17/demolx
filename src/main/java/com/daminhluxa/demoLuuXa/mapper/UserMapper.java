package com.daminhluxa.demoLuuXa.mapper;

import com.daminhluxa.demoLuuXa.dto.UserCreationRequest;
import com.daminhluxa.demoLuuXa.dto.UserUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.UserResponse;
import com.daminhluxa.demoLuuXa.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserResponse toUserResponse(User user);
}
