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
import com.example.hellomovie.global.security.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                .orElseThrow(() -> new OpenException(ErrorCode.USER_NOT_FOUND));

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
    public UserDto getLoginUser(UserDetails userDetails) {
        if(Objects.isNull(userDetails)){
            UserDto userDto = new UserDto();
            userDto.setUserId("");
            return userDto;
        }
        User user = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new OpenException(ErrorCode.USER_NOT_FOUND));
        return UserDto.fromEntity(user);
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
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.USER));
        log.info("loadUserByUsername");
        return new org.springframework.security.core.userdetails.User
                (user.getUserId(), user.getPassword(), grantedAuthorities);
    }

}
