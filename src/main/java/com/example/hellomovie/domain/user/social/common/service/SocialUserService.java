package com.example.hellomovie.domain.user.social.common.service;

import com.example.hellomovie.domain.user.site.dto.RegisterUser;
import com.example.hellomovie.domain.user.social.common.dto.SocialUserDto;
import com.example.hellomovie.domain.user.social.kakao.dto.SocialUserInfo;

public interface SocialUserService {

    /**
     * 소셜 유저 회원가입 임시
     */
    void registerTemporarily(SocialUserInfo info);

    /**
     * 소셜 유저 회원가입 완료
     */
    SocialUserDto registerFinally(RegisterUser input);

    /**
     * 닉네임 존재하는지 확인
     */
    boolean existsByNickname(String nickname);

    /**
     * userId(이메일)로 유저 찾기
     */
    SocialUserDto getByUserId(String userId);
}
