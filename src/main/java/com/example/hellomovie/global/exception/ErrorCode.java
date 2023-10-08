package com.example.hellomovie.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //유저에게 노출할 에러 ===================================================================================
    //basic
    NOT_FOUND_ERROR_404(HttpStatus.NOT_FOUND.value(), "페이지를 찾지 못했습니다. 주소가 잘못되었거나 더 이상 제공되지 않는 페이지 입니다."),

    //user
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "해당 이용자를 찾을 수 없습니다."),
    USER_STOPPED(HttpStatus.FORBIDDEN.value(), "정지된 회원 입니다."),

    //email
    EMAIL_AUTH_ALREADY_COMPLETE(HttpStatus.ALREADY_REPORTED.value(), "이미 이메일 인증이 완료된 이메일 입니다."),
    EMAIL_AUTH_REQUIRED(HttpStatus.ALREADY_REPORTED.value(), "이메일 인증 활성화 이후에 로그인 해 주세요."),


    //개발자 전용 =============================================================================================
    NO_AUTHORITY_ERROR(HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND.value(), "페이지를 찾지 못했습니다. 주소가 잘못되었거나 더 이상 제공되지 않는 페이지 입니다."),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부 서버 오류가 발생 했습니다.");

    private final int statusCode;
    private final String description;

}
