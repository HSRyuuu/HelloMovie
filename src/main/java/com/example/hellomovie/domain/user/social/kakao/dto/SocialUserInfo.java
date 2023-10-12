package com.example.hellomovie.domain.user.social.kakao.dto;

import com.example.hellomovie.global.auth.type.UserType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SocialUserInfo {
    private String email;
    private String nickname;
    private String accessToken;
    private UserType userType;
}
