package com.example.hellomovie.domain.user.site.dto;

import com.example.hellomovie.domain.user.site.persist.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterUserResult {
    private boolean successYn;
    private Long id;
    private String userId;
    private String nickname;
    private String emailAuthKey;

    public RegisterUserResult(boolean successYn){
        this.successYn = successYn;
    }

    public RegisterUserResult (boolean successYn, User user){
        this.id = user.getId();
        this.successYn = successYn;
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.emailAuthKey = user.getEmailAuthKey();
    }

}
