package com.example.hellomovie.domain.user.service;

import com.example.hellomovie.domain.user.dto.RegisterUser;
import com.example.hellomovie.domain.user.persist.User;
import com.example.hellomovie.domain.user.persist.UserRepository;
import com.example.hellomovie.domain.user.type.UserStatus;
import com.example.hellomovie.global.exception.ErrorCode;
import com.example.hellomovie.global.exception.MyException;
import com.example.hellomovie.global.mail.MailComponents;
import com.example.hellomovie.global.mail.SendMailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailComponents mailComponents;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public boolean register(RegisterUser input) {
        //user 존재 여부 확인
        if(userRepository.existsByUserId(input.getUserId())){
            log.info("[RegisterUser error] {} : 이미 존재 하는 userId 입니다.", input.getUserId());
            return false;
        }

        User user = User.registerUser(input);
        userRepository.save(user);

        mailComponents.sendMailForRegister(new SendMailDto(user.getUserId(), user.getNickname(), user.getEmailAuthKey()));

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        User user = userRepository.findByEmailAuthKey(uuid)
                .orElseThrow(() -> new MyException(ErrorCode.USER_NOT_FOUND));

        if(user.isEmailAuthYn()){
            log.info("Email Auth - {} : 이미 활성화 된 유저입니다", user.getUserId());
            return true;
        }

        user.setUserStatus(UserStatus.ING);
        user.setEmailAuthDt(LocalDateTime.now());
        user.setEmailAuthYn(true);
        userRepository.save(user);

        log.info("[Email Auth Complete ] : userId = {}", user.getUserId());
        return true;
    }



}
