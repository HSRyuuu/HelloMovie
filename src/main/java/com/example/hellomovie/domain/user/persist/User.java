package com.example.hellomovie.domain.user.persist;

import com.example.hellomovie.domain.user.type.RegisterType;
import com.example.hellomovie.domain.user.type.UserStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Getter @Setter
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
}
