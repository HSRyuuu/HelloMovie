package com.example.hellomovie.domain.user.service;

import com.example.hellomovie.domain.user.dto.RegisterUser;
import com.example.hellomovie.domain.user.dto.UserDto;
import com.example.hellomovie.domain.user.persist.User;
import com.example.hellomovie.domain.user.persist.UserRepository;
import com.example.hellomovie.domain.user.type.UserStatus;
import com.example.hellomovie.global.exception.CustomException;
import com.example.hellomovie.global.exception.ErrorCode;
import com.example.hellomovie.global.exception.OpenException;
import com.example.hellomovie.global.mail.MailComponents;
import com.example.hellomovie.global.mail.SendMailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailComponents mailComponents;


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
    public UserDto getUserByUserDetails(UserDetails userDetails) {
        if(Objects.isNull(userDetails)){
            log.info("EmptyUser");
            return UserDto.emptyUser();
        }
        Optional<User> findUser = userRepository.findByUserId(userDetails.getUsername());
        if(findUser.isPresent()){
            return UserDto.fromEntity(findUser.get());
        }else{
            log.info("EmptyUser");
            return UserDto.emptyUser();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username)
                        .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));
        if(UserStatus.EMAIL_AUTH_REQUIRED.equals(user.getUserStatus())){
            throw new OpenException(ErrorCode.EMAIL_AUTH_REQUIRED);
        }
        if(UserStatus.STOP.equals(user.getUserStatus())){
            throw new OpenException(ErrorCode.USER_STOPPED);
        }

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), user.getAuthorities());
    }

}
