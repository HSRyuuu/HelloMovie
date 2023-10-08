package com.example.hellomovie.domain.user.dto;

import com.example.hellomovie.domain.user.persist.User;
import com.example.hellomovie.domain.user.type.RegisterType;
import com.example.hellomovie.domain.user.type.UserStatus;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId; //email
    private String password;

    private String name;
    private String nickname;

    private String accessToken;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private RegisterType registerType;
    private UserStatus userStatus;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .nickname(user.getNickname())
                .accessToken(user.getAccessToken())
                .emailAuthYn(user.isEmailAuthYn())
                .emailAuthKey(user.getEmailAuthKey())
                .emailAuthDt(user.getEmailAuthDt())
                .registerType(user.getRegisterType())
                .userStatus(user.getUserStatus())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .build();
    }


}
