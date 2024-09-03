package com.daminhluxa.demoLuuXa.service;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import com.daminhluxa.demoLuuXa.dto.UserCreationRequest;
import com.daminhluxa.demoLuuXa.dto.UserUpdateRequest;
import com.daminhluxa.demoLuuXa.dto.response.UserResponse;
import com.daminhluxa.demoLuuXa.entity.User;
import com.daminhluxa.demoLuuXa.exception.AppException;
import com.daminhluxa.demoLuuXa.exception.GlobalExceptionHandler;
import com.daminhluxa.demoLuuXa.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private GlobalExceptionHandler exceptionHandler;
    private UserCreationRequest request;
    private UserUpdateRequest requestUpdate;
    private UserResponse response;
    private User user;
    private LocalDate dob;

    @BeforeEach
    public void setUp() {
        dob = LocalDate.of(1999, 1, 1);

        request = UserCreationRequest.builder()
                .username("john")
                .password("123456")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .email("john@doe.com")
                .build();

        response = UserResponse.builder()
                .id("371594bfdc49")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .email("john@doe.com")
                .build();

        user = User.builder()
                .id("371594bfdc49")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .email("john@doe.com")
                .build();

        requestUpdate = UserUpdateRequest.builder()
                .password("123456")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .email("john@doe.com")
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        //GIVEN
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(false);
        Mockito.when(userRepository.save(any())).thenReturn(user);

        //WHEN
        var result = userService.createUser(request);

        //THEN
        Assertions.assertThat(response.getId()).isEqualTo("371594bfdc49");
        Assertions.assertThat(result.getUsername()).isEqualTo("john");
    }

    @Test
    void createUser_userExisted_fail() {
        //GIVEN
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);

        //WHEN
         var exception = org.junit.jupiter.api.Assertions.assertThrows(AppException.class,
                () -> userService.createUser(request));

        //THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(402);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User already existed");
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_validRequest_success() {
        //GIVEN
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        //WHEN
        var result = userService.getMyInfo();
        //THEN
        Assertions.assertThat(result.getId()).isEqualTo("371594bfdc49");
        Assertions.assertThat(result.getUsername()).isEqualTo("john");
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_userNotFound_fail() {
        //GIVEN
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        var exception = org.junit.jupiter.api.Assertions.assertThrows(AppException.class,
                () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(404);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User Not Existed");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_validRequest_success() {
        //GIVEN
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

       var result = userService.getAllUsers();
        //THEN
        Assertions.assertThat(result.size()).isNotZero();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUser_validRequest_success() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        var result = userService.getUser(response.getId());

        Assertions.assertThat(result.getId()).isEqualTo("371594bfdc49");
        Assertions.assertThat(result.getUsername()).isEqualTo("john");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUser_userNotFound_fail() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        var exception = org.junit.jupiter.api.Assertions.assertThrows(AppException.class,
                () -> userService.getUser(response.getId()));

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(404);
        Assertions.assertThat(exception.getErrorCode().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUser_unauthorized_fail() {

        var exception = org.junit.jupiter.api.Assertions.assertThrows(AccessDeniedException.class,
                () -> userService.getUser(response.getId()));

        Assertions.assertThat(exception)
                .isInstanceOf(AccessDeniedException.class);
        ResponseEntity<APIResponse> responseEntity =  exceptionHandler.accessDeniedExceptionHandler(new AccessDeniedException("Access is denied"));
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(406);
    }

//    @Test
//    @WithMockUser(username = "john")
//    void updateUser_validRequest_success() {
//        String id = "371594bfdc49";
//        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
//        Mockito.when(userRepository.save(any())).thenReturn(user);\
//        var result = userService.updateUser(id, requestUpdate);
//
//        Assertions.assertThat(result.getId()).isEqualTo("371594bfdc49");
//        Assertions.assertThat(result.getUsername()).isEqualTo("john");
//    }

}
