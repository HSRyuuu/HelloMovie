package com.example.hellomovie.domain.user.site.service;

import com.example.hellomovie.domain.user.site.dto.RegisterUserResult;
import com.example.hellomovie.domain.user.site.model.RegisterUser;
import com.example.hellomovie.domain.user.site.dto.UserDto;
import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.site.persist.UserRepository;
import com.example.hellomovie.domain.user.social.common.SocialUserRegister;
import com.example.hellomovie.global.auth.type.UserType;
import com.example.hellomovie.global.exception.CustomException;
import com.example.hellomovie.global.exception.ErrorCode;
import com.example.hellomovie.global.util.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("유저 회원 가입")
    class register{

        @Test
        @DisplayName("성공")
        void register_success() {
            //given
            RegisterUser input = RegisterUser.builder()
                    .userId("test@gmail.com")
                    .password("test!")
                    .name("test_name")
                    .nickname("test_nickname")
                    .build();

            //when
            RegisterUserResult result = userService.register(input);
            log.info("RegisterUserResult : {}", result);

            User findUser = userRepository.findById(result.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            log.info("findUser : {}", findUser);

            //then
            assertThat(result.getId()).isEqualTo(findUser.getId());
            assertThat(input.getUserId()).isEqualTo(findUser.getUserId());
            assertThat(
                    PasswordUtils.equalsPlainTextAndHashed(input.getPassword(), findUser.getPassword()))
                    .isTrue();
        }

        @Test
        @DisplayName("실패 : 이미 존재 하는 회원인 경우")
        void register_USER_ALREADY_EXIST() {
            //given
            RegisterUser inputBefore = RegisterUser.builder()
                    .userId("test@gmail.com")
                    .password("test!")
                    .name("test_name")
                    .nickname("test_nickname")
                    .build();
            userService.register(inputBefore);

            RegisterUser input = RegisterUser.builder()
                    .userId("test@gmail.com")
                    .password("test!")
                    .name("test_name")
                    .nickname("test_nickname")
                    .build();

            //when
            RegisterUserResult result = userService.register(input);
            log.info("RegisterUserResult : {}", result);

            //then
            assertThat(result.isSuccessYn()).isFalse();
        }
    }
    @Nested
    @DisplayName("소셜 유저 회원 가입")
    class registerSocialUser{

        @Test
        @DisplayName("성공")
        void registerSocialUser(){
            //given
            SocialUserRegister socialUserRegister =
                    SocialUserRegister.builder()
                            .userId("test@test.com")
                            .name("test@test.com_123123")
                            .nickname("test_nickname")
                            .userType(UserType.KAKAO)
                            .createdAt(LocalDateTime.now())
                            .build();
            //when
            User user = userService.registerSocialUser(socialUserRegister);

            //then
            assertThat(socialUserRegister.getUserId()).isEqualTo(user.getUserId());
            assertThat(user.getName().startsWith(user.getUserId())).isTrue();
            assertThat(socialUserRegister.getUserType()).isEqualTo(user.getUserType());

        }

    }

    @Test
    void socialUserRegister() {
    }

    @Test
    void userExists() {
    }

    @Test
    void emailAuth() {
    }

    @Test
    void getPrincipalDetails() {
    }
}