package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.constant.PredefinedRole;
import com.daminhluxa.demoLuuXa.dto.UserCreationRequest;
import com.daminhluxa.demoLuuXa.dto.UserUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.UserResponse;
import com.daminhluxa.demoLuuXa.entity.Role;
import com.daminhluxa.demoLuuXa.entity.User;
import com.daminhluxa.demoLuuXa.exception.AppException;
import com.daminhluxa.demoLuuXa.exception.ErrorCode;
import com.daminhluxa.demoLuuXa.mapper.UserMapper;
import com.daminhluxa.demoLuuXa.repository.RoleRepository;
import com.daminhluxa.demoLuuXa.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext();
        String name = authentication.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
