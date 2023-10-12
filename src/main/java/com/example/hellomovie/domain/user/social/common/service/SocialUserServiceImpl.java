package com.example.hellomovie.domain.user.social.common.service;

import com.example.hellomovie.domain.user.site.dto.RegisterUser;
import com.example.hellomovie.domain.user.site.persist.UserRepository;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.domain.user.social.common.dto.SocialUserDto;
import com.example.hellomovie.domain.user.social.common.persist.SocialUser;
import com.example.hellomovie.domain.user.social.common.persist.SocialUserRepository;
import com.example.hellomovie.domain.user.social.kakao.dto.SocialUserInfo;
import com.example.hellomovie.global.exception.CustomException;
import com.example.hellomovie.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SocialUserServiceImpl implements SocialUserService{

    private final SocialUserRepository socialUserRepository;
    private final UserRepository userRepository;


    @Override
    public void registerTemporarily(SocialUserInfo info) {
        SocialUser socialUser = SocialUser.builder()
                .userId(info.getEmail())
                .name(info.getNickname())
                .password(BCrypt.hashpw("kakao", BCrypt.gensalt()))
                .accessToken(info.getAccessToken())
                .userType(info.getUserType())
                .userStatus(UserStatus.REGISTER_NOT_FINISHED)
                .build();
        socialUserRepository.save(socialUser);
    }

    @Override
    public SocialUserDto registerFinally(RegisterUser input) {
            SocialUser socialUser = socialUserRepository.findByUserId(input.getUserId())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "SocialUserService.registerFinally"));
            socialUser.setNickname(input.getNickname());
            socialUser.setUserStatus(UserStatus.ING);
            socialUserRepository.save(socialUser);
            return SocialUserDto.fromEntity(socialUser);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname) || socialUserRepository.existsByNickname(nickname);
    }

    @Override
    public SocialUserDto getByUserId(String userId) {
        SocialUser socialUser = socialUserRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "SocialUserService.getByUserId"));
        return SocialUserDto.fromEntity(socialUser);
    }
}
