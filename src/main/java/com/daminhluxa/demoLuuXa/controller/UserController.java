package com.daminhluxa.demoLuuXa.controller;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.UserCreationRequest;
import com.daminhluxa.demoLuuXa.dto.UserUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.UserResponse;
import com.daminhluxa.demoLuuXa.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    UserService userService;

    @PostMapping
    public APIResponse<UserResponse> addUser(@RequestBody @Valid UserCreationRequest request) {
        return APIResponse.<UserResponse>builder()
                .data(userService.createUser(request))
                .build();
    }

    @GetMapping
    public APIResponse<List<UserResponse>> getAllUsers() {
        return APIResponse.<List<UserResponse>>builder()
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("/myInfo")
    public APIResponse<UserResponse> getMyInfo() {
        return APIResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .build();
    }

    @GetMapping("/{userId}")
    public APIResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return APIResponse.<UserResponse>builder()
                .data(userService.getUser(userId))
                .build();
    }

    @PutMapping("/{userId}")
    public APIResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest user) {
        return APIResponse.<UserResponse>builder()
                .data(userService.updateUser(userId, user))
                .build();
    }

    @DeleteMapping("/{userId}")
    public APIResponse<String> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return APIResponse.<String>builder()
                .data("User has been deleted")
                .build();
    }
}
