package com.example.hellomovie.domain.user.social.common;

import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.social.kakao.model.KakaoProfile;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.auth.type.UserType;
import com.example.hellomovie.global.util.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class SocialUserRegister {
    private String userId;
    private String name;
    private String nickname;
    private UserType userType;
    private LocalDateTime createdAt;

    public static SocialUserRegister fromKakao(KakaoProfile kakaoProfile){
        return SocialUserRegister.builder()
                .userId(kakaoProfile.getEmail())
                .name(kakaoProfile.getEmail() + "_" + kakaoProfile.getId())
                .nickname(kakaoProfile.getNickname())
                .userType(UserType.KAKAO)
                .createdAt(kakaoProfile.getConnectedAt())
                .build();
    }

    public static User toEntity(SocialUserRegister socialUser){
        String password = UUID.randomUUID().toString().substring(0, 6);
        String encPassword = PasswordUtils.encPassword(password);

        return User.builder()
                .userId(socialUser.getUserId())
                .password(encPassword)
                .name(socialUser.getName())
                .nickname(socialUser.getNickname())
                .emailAuthYn(true)
                .emailAuthDt(socialUser.getCreatedAt())
                .userType(socialUser.getUserType())
                .userStatus(UserStatus.ING)
                .createdAt(socialUser.getCreatedAt())
                .lastModifiedAt(socialUser.getCreatedAt())
                .build();
    }


}
