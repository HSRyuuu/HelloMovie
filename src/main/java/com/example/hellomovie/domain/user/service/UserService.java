package com.example.hellomovie.domain.user.service;


import com.example.hellomovie.domain.user.dto.RegisterUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean register(RegisterUser input);
    boolean emailAuth(String uuid);
}
