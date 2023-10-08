package com.example.hellomovie.domain.user.persist;

import com.example.hellomovie.domain.user.dto.RegisterUser;
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
@Entity
public class User {

    @Id
    private String userId; //email
    private String password;

    private String name;
    private String nickname;

    private String accessToken;

    //이메일 인증 관련
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    @Enumerated(EnumType.STRING)
    private RegisterType registerType;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static User registerUser(RegisterUser input) {
        String encPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());

        return User.builder()
                .userId(input.getUserId())
                .password(encPassword)
                .name(input.getName())
                .nickname(input.getNickname())
                .accessToken("site-register")
                .emailAuthKey(UUID.randomUUID().toString())
                .registerType(RegisterType.SITE)
                .userStatus(UserStatus.EMAIL_AUTH_REQUIRED)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }
}
