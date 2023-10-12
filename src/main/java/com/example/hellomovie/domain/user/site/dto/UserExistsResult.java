package com.example.hellomovie.domain.user.site.dto;

import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.auth.type.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExistsResult {

    private boolean existYn;
    private UserType userType;
    private UserStatus userStatus;
}
