package com.daminhluxa.demoLuuXa.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNAUTHENTICATED(405, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    USER_NOT_EXISTED(404, "User Not Existed", HttpStatus.NOT_FOUND),
    PASSWORD_INVALID(403, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(401, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    UNCATEGORIED_EXCEPTION(999, "Uncategoried Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(402, "User already existed", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(406, "Unauthorized", HttpStatus.FORBIDDEN),
    INVALID_DOB(407, "You must be at least {min}", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String msg, HttpStatus statusCode) {
        this.code = code;
        this.msg = msg;
        this.statusCode = statusCode;
    }

    private int code;
    private String msg;
    private HttpStatusCode statusCode;
}
