package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.RoleRequest;
import com.daminhluxa.demoLuuXa.dto.response.RoleResponse;
import com.daminhluxa.demoLuuXa.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    public APIResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return APIResponse.<RoleResponse>builder()
                .data(roleService.create(roleRequest))
                .build();
    }

    @GetMapping
    public APIResponse<List<RoleResponse>> getRoles() {
        return APIResponse.<List<RoleResponse>>builder()
                .data(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    public APIResponse<Void> deleteRole(@PathVariable("role") String role) {
        roleService.delete(role);
        return APIResponse.<Void>builder().build();
    }
}
