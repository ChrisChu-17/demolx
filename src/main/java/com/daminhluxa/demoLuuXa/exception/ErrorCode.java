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
    INVALID_DOB(407, "You must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_SPIRIT_ROLE(408, "Your spirit role is not in system", HttpStatus.BAD_REQUEST),
    SPIRITUAL_GUIDE_HAS_BEEN_USED(409, "Spirit guid has been used", HttpStatus.BAD_REQUEST),
    DORM_EXISTED(410, "Dorm already existed", HttpStatus.BAD_REQUEST),
    DORM_NOT_FOUND(411, "Dorm not found", HttpStatus.BAD_REQUEST),
    SCHOOL_EXISTED(412, "School already existed", HttpStatus.BAD_REQUEST),
    SCHOOL_NOT_FOUND(413, "School not found", HttpStatus.BAD_REQUEST),
    MAJOR_NOT_FOUND(414, "Major not found", HttpStatus.BAD_REQUEST),
    STUDENT_NOT_FOUND(415, "Student not found", HttpStatus.BAD_REQUEST),
    TRANSCRIPT_NOT_FOUND(416, "Transcript not found", HttpStatus.BAD_REQUEST),
    INVALID_TRANSCRIPT_WITH_MIN(417, "GPA must be higher 0.0 or equal", HttpStatus.BAD_REQUEST),
    INVALID_TRANSCRIPT_WITH_MAX(418, "GPA must be lower than 4.0 or equal", HttpStatus.BAD_REQUEST),
    INVALID_TRANSCRIPT_GRADE(419, "Grade is not blanked", HttpStatus.BAD_REQUEST),
    INVALID_TRANSCRIPT_SEMESTER(420, "Semester is not blanked", HttpStatus.BAD_REQUEST),
    INVALID_TRANSCRIPT_SCHOOL_YEAR(421, "School year is not blanked", HttpStatus.BAD_REQUEST),
    EXECUTIVE_BOARD_EXISTED(422, "Board already existed", HttpStatus.BAD_REQUEST),

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
