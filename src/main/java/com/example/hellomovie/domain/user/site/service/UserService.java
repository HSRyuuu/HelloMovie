package com.example.hellomovie.domain.user.site.service;


import com.example.hellomovie.domain.user.site.dto.RegisterUserResult;
import com.example.hellomovie.domain.user.site.model.RegisterUser;
import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.social.common.SocialUserRegister;

public interface UserService{
    /**
     * 사이트 유저 회원가입
     */
    RegisterUserResult register(RegisterUser input);

    /**
     * 소셜 로그인 유저 회원가입
     */
    User registerSocialUser(SocialUserRegister socialUserRegister);

    /**
     * 유저 찾기
     */
    boolean userExists(String userId);

    /**
     * 이메일 인증 - 인증정보 수정
     */
    boolean emailAuth(String uuid);

}
