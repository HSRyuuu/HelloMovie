package com.example.hellomovie.domain.user.site.dto;

import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.auth.type.UserType;
import com.example.hellomovie.global.util.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterUser {
    private String userId; //email

    private String password;
    private String passwordCheck;

    private String name;
    private String nickname;

    public static User toEntity(RegisterUser input){
        String encPassword = PasswordUtils.encPassword(input.getPassword());

        return User.builder()
                .userId(input.getUserId())
                .password(encPassword)
                .name(input.getName())
                .nickname(input.getNickname())
                .emailAuthKey(UUID.randomUUID().toString())
                .userType(UserType.SITE)
                .userStatus(UserStatus.EMAIL_AUTH_REQUIRED)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }

}
