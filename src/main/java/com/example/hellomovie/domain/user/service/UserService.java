package com.example.hellomovie.domain.user.service;


import com.example.hellomovie.domain.user.dto.RegisterUser;
import com.example.hellomovie.domain.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean register(RegisterUser input);
    boolean emailAuth(String uuid);
    UserDto getUserByUserDetails(UserDetails userDetails);
}
