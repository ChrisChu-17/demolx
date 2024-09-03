package com.daminhluxa.demoLuuXa.service;


import com.daminhluxa.demoLuuXa.dto.RoleRequest;
import com.daminhluxa.demoLuuXa.dto.response.RoleResponse;
import com.daminhluxa.demoLuuXa.entity.Role;
import com.daminhluxa.demoLuuXa.mapper.RoleMapper;
import com.daminhluxa.demoLuuXa.repository.PermissionRepository;
import com.daminhluxa.demoLuuXa.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        log.info("create role request: {}", request);
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
