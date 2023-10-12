package com.example.hellomovie.global.auth.type;

public enum UserStatus {
    EMAIL_AUTH_REQUIRED, //이메일 인증 미완료
    REGISTER_NOT_FINISHED, //소셜 로그인 시 회원가입 절차 미완료
    ING, //이용중
    STOP, //정지
    WITHDRAW //탈퇴
}
