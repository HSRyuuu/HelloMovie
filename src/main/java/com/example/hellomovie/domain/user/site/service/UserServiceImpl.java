package com.example.hellomovie.domain.user.site.service;

import com.example.hellomovie.domain.user.site.dto.RegisterUser;
import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.site.persist.UserRepository;
import com.example.hellomovie.domain.user.social.common.SocialUserRegister;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.exception.CustomException;
import com.example.hellomovie.global.exception.ErrorCode;
import com.example.hellomovie.global.exception.OpenException;
import com.example.hellomovie.global.mail.MailComponents;
import com.example.hellomovie.global.mail.SendMailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailComponents mailComponents;


    @Override
    @Transactional
    public boolean register(RegisterUser input) {
        //user 존재 여부 확인
        if(userRepository.existsByUserId(input.getUserId())){
            log.info("[RegisterUser error] {} : 이미 존재 하는 userId 입니다.", input.getUserId());
            return false;
        }

        User user = RegisterUser.toEntity(input);
        userRepository.save(user);

        mailComponents.sendMailForRegister(new SendMailDto(user.getUserId(), user.getNickname(), user.getEmailAuthKey()));

        return true;
    }

    @Override
    @Transactional
    public User socialUserRegister(SocialUserRegister socialUserRegister) {
        User user = SocialUserRegister.toEntity(socialUserRegister);
        return userRepository.save(user);
    }

    @Override
    public boolean userExists(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public boolean emailAuth(String uuid) {
        User user = userRepository.findByEmailAuthKey(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "UserService.emailAuth"));

        if(user.isEmailAuthYn()){
            throw new OpenException(ErrorCode.EMAIL_AUTH_ALREADY_COMPLETE);
        }

        user.setUserStatus(UserStatus.ING);
        user.setEmailAuthDt(LocalDateTime.now());
        user.setEmailAuthYn(true);
        userRepository.save(user);

        log.info("[Email Auth Complete ] : userId = {}", user.getUserId());
        return true;
    }

    @Override
    public PrincipalDetails getPrincipalDetails(String email) {
        User user = userRepository.findByUserId(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "UserService.getPrincipalDetails"));
        return new PrincipalDetails(user);
    }
}
