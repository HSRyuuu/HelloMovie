package com.example.hellomovie.domain.user.site.dto;

import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.auth.type.UserType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String userId; //email
    private String password;

    private String name;
    private String nickname;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private UserType userType;
    private UserStatus userStatus;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .nickname(user.getNickname())
                .emailAuthYn(user.isEmailAuthYn())
                .emailAuthKey(user.getEmailAuthKey())
                .emailAuthDt(user.getEmailAuthDt())
                .userType(user.getUserType())
                .userStatus(user.getUserStatus())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .build();
    }


}
