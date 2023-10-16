package com.example.hellomovie.domain.user.social.kakao.controller;

import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.site.service.UserService;
import com.example.hellomovie.domain.user.social.common.SocialUserRegister;
import com.example.hellomovie.domain.user.social.kakao.api.KakaoApi;
import com.example.hellomovie.domain.user.social.kakao.model.KakaoProfile;
import com.example.hellomovie.domain.user.social.kakao.model.OAuthToken;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.auth.service.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@Controller
public class KakaoAuthController {

    private final KakaoApi kakaoApi;
    private final UserService userService;
    private final AuthUtils authUtils;

    @RequestMapping("/login/oauth2/code/kakao")
    public String kakaoLogin(@RequestParam String code, HttpSession session){
        // ===== 카카오 api =====
        // 1. 인가 코드 받기(controller)
        // 2. 토큰 받기
        OAuthToken oAuthToken = kakaoApi.getOAuthToken(code);
        // 3. 사용자 정보 받기
        KakaoProfile kakaoProfile = kakaoApi.getUserInfo(oAuthToken.getAccess_token());
        // ===== 카카오 api 끝 =====

        // ===== Spring Security 로그인 처리 =====
        // 1. 유저 존재 여부 확인
        boolean result = userService.userExists(kakaoProfile.getEmail());

        PrincipalDetails principalDetails;
        // 2-1. result == false : 회원 가입
        if(!result){
            SocialUserRegister socialUserRegister = SocialUserRegister.fromKakao(kakaoProfile);
            User user = userService.registerSocialUser(socialUserRegister);
            principalDetails = new PrincipalDetails(user);
        }// 2-2. result == true : 로그인 처리
        else{
            principalDetails = authUtils.getPrincipalDetails(kakaoProfile.getEmail());
        }
        log.info("[카카오 로그인] {}", principalDetails);
        // 3. 로그인 처리
        authUtils.loadUserDirectly(principalDetails, session);

        // 4-1. 이번에 회원가입 했다면, 회원가입 완료 안내페이지로 이동
        if(!result){
            return "redirect:/register/social";
        }
        // 4-2. 로그인만 했다면, home으로 이동
        return "redirect:/board/home";
    }



}
