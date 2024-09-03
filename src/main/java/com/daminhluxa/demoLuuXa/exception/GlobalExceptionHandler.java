package com.daminhluxa.demoLuuXa.exception;

import com.daminhluxa.demoLuuXa.dto.APIResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_VALUE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<APIResponse> runtimeExceptionHandler(RuntimeException e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIED_EXCEPTION.getCode());
        apiResponse.setMsg(ErrorCode.UNCATEGORIED_EXCEPTION.getMsg());
        return  ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String enumKey= e.getFieldError().getDefaultMessage();
        Map<String,Object> attributes = null;
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);

        var constraintViolation = e.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);
        attributes = constraintViolation.getConstraintDescriptor().getAttributes();

        log.info(attributes.toString());


        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMsg(Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMsg(), attributes)
                        : errorCode.getMsg()
                );
        return  ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<APIResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
       ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
       return ResponseEntity.status(errorCode.getStatusCode()).body(
               APIResponse.builder()
                       .code(errorCode.getCode())
                       .msg(errorCode.getMsg())
                       .build()
       );
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse> appExceptionHandler(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();

        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMsg(errorCode.getMsg());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    private String mapAttribute(String msg, Map<String, Object> attributes) {
        String minValue = attributes.get(MIN_VALUE).toString();

        return msg.replace("{" + MIN_VALUE + "}", minValue);
    }
}
