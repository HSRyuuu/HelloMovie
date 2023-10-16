package com.example.hellomovie.domain.user.site.service;

import com.example.hellomovie.domain.user.site.dto.RegisterUserResult;
import com.example.hellomovie.domain.user.site.model.RegisterUser;
import com.example.hellomovie.domain.user.site.dto.UserDto;
import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.site.persist.UserRepository;
import com.example.hellomovie.domain.user.social.common.SocialUserRegister;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.exception.CustomException;
import com.example.hellomovie.global.exception.ErrorCode;
import com.example.hellomovie.global.exception.OpenException;
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

    @Override
    @Transactional
    public RegisterUserResult register(RegisterUser input) {
        //user 존재 여부 확인
        if(userRepository.existsByUserId(input.getUserId())){
            log.info("[RegisterUser error] {} : 이미 존재 하는 userId 입니다.", input.getUserId());
            return new RegisterUserResult(false);
        }

        User user = RegisterUser.toEntity(input);
        userRepository.save(user);

        return new RegisterUserResult(true, user);
    }

    @Override
    @Transactional
    public User registerSocialUser(SocialUserRegister socialUserRegister) {
        return userRepository.save(
                SocialUserRegister.toEntity(socialUserRegister));
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
}
