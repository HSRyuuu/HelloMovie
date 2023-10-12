package com.example.hellomovie.domain.user.site.service;


import com.example.hellomovie.domain.user.site.dto.RegisterUser;

public interface UserService{
    /**
     * 사이트 유저 회원가입
     */
    boolean register(RegisterUser input);


    /**
     * 이메일 인증 - 인증정보 수정
     */
    boolean emailAuth(String uuid);

}
