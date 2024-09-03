package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.PermissionRequest;
import com.daminhluxa.demoLuuXa.dto.response.PermissionResponse;
import com.daminhluxa.demoLuuXa.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @GetMapping
    public APIResponse<List<PermissionResponse>> getPermissions() {
        return APIResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAll())
                .build();
    }

    @PostMapping
    public APIResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return  APIResponse.<PermissionResponse>builder()
                .data(permissionService.create(request))
                .build();
    }

    @DeleteMapping("/{permission}")
    public APIResponse<Void> deletePermission(@PathVariable("permission") String permission) {
        permissionService.delete(permission);
        return APIResponse.<Void>builder().build();
    }
}
