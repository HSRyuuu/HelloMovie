package com.example.hellomovie.domain.user.service;


public interface UserService{

    boolean register();
    boolean userExists(String userId);
}
