package com.example.hellomovie.global.auth.dto;

import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.auth.type.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResult {

    private boolean userExistYn;
    private boolean passwordCorrectYn;

    private String userId; //email
    private String password;

    private String name;
    private String nickname;

    private String accessToken;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private UserType userType;
    private UserStatus userStatus;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public List<String> getRoles(){
        List<String> list = new ArrayList<>();
        list.add("ROLE_USER");
        if(this.userType.equals(UserType.ADMIN)){
            list.add("ROLE_ADMIN");
        }
        return list;
    }

    public static LoginResult fromEntity(User user) {
        return LoginResult.builder()
                .userExistYn(true)
                .passwordCorrectYn(true)
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
