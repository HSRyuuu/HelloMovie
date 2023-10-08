package com.example.hellomovie.global.security;

import com.example.hellomovie.domain.user.persist.User;
import com.example.hellomovie.domain.user.persist.UserRepository;
import com.example.hellomovie.global.exception.ErrorCode;
import com.example.hellomovie.global.exception.OpenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecurityUser {
    private final UserRepository userRepository;

    public User findUser(String username){
        return userRepository.findByUserId(username)
                .orElseThrow(() -> new OpenException(ErrorCode.USER_NOT_FOUND));
    }
}
