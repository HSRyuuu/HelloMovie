package com.example.hellomovie.domain.user.social.common.dto;

import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.domain.user.social.common.persist.SocialUser;
import com.example.hellomovie.global.auth.type.UserType;
import lombok.*;

@Data
@ToString
@Builder
public class SocialUserDto {

    private Long id;
    private String userId;

    private String name;
    private String nickname;

    private UserType userType;

    private UserStatus userStatus;

    private String accessToken;

    public static SocialUserDto fromEntity(SocialUser socialUser){
        return SocialUserDto.builder()
                .id(socialUser.getId())
                .userId(socialUser.getUserId())
                .nickname(socialUser.getNickname())
                .userType(socialUser.getUserType())
                .userStatus(socialUser.getUserStatus())
                .accessToken(socialUser.getAccessToken())
                .build();
    }

}
