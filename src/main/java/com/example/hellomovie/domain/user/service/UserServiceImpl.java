package com.example.hellomovie.domain.user.service;

import com.example.hellomovie.domain.user.persist.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public boolean register() {

        return false;
    }

    @Override
    public boolean userExists(String userId) {

        return false;
    }
}
