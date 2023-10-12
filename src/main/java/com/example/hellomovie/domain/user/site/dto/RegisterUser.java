package com.example.hellomovie.domain.user.site.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


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

}
