package com.example.hellomovie.domain.user.site.persist;

import com.example.hellomovie.domain.user.site.dto.RegisterUser;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.auth.type.UserType;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User{

    @Id
    private String userId; //email
    private String password;

    private String name;
    private String nickname;

    //이메일 인증 관련
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    @Enumerated(EnumType.STRING)
    private UserType userType;
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
                .emailAuthKey(UUID.randomUUID().toString())
                .userType(UserType.SITE)
                .userStatus(UserStatus.EMAIL_AUTH_REQUIRED)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }

    public static PrincipalDetails toPrincipalDetails(User user){
        return PrincipalDetails.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .nickname(user.getNickname())
                .userType(UserType.SITE)
                .build();
    }


}
