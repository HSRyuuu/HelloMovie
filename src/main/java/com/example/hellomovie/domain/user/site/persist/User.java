package com.example.hellomovie.domain.user.site.persist;

import com.example.hellomovie.domain.user.site.dto.RegisterUser;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.auth.type.UserType;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


}
