package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.dto.PermissionRequest;
import com.daminhluxa.demoLuuXa.dto.response.PermissionResponse;
import com.daminhluxa.demoLuuXa.entity.Permission;
import com.daminhluxa.demoLuuXa.mapper.PermissionMapper;
import com.daminhluxa.demoLuuXa.repository.PermissionRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var list= permissionRepository.findAll();
        return list.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }

}
