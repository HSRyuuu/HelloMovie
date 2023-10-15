package com.example.hellomovie.global.auth.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        return null;
    }
}
