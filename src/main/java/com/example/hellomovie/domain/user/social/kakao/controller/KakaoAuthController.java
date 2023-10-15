package com.example.hellomovie.domain.user.social.kakao.controller;

import com.example.hellomovie.domain.user.site.dto.RegisterUser;
import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.site.service.UserService;
import com.example.hellomovie.domain.user.social.common.SocialUserRegister;
import com.example.hellomovie.domain.user.social.kakao.api.KakaoApi;
import com.example.hellomovie.domain.user.social.kakao.model.KakaoProfile;
import com.example.hellomovie.domain.user.social.kakao.model.OAuthToken;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RequestMapping()
@Controller
public class KakaoAuthController {

    private final KakaoApi kakaoApi;
    private final UserService userService;
    private final AuthService authService;

    @RequestMapping("/login/oauth2/code/kakao")
    public String kakaoLogin(@RequestParam String code, HttpSession session){
        // 1. 인가 코드 받기(controller)
        // 2. 토큰 받기
        OAuthToken oAuthToken = kakaoApi.getOAuthToken(code);
        // 3. 사용자 정보 받기
        KakaoProfile kakaoProfile = kakaoApi.getUserInfo(oAuthToken.getAccess_token());

        boolean result = userService.userExists(kakaoProfile.getEmail());

        PrincipalDetails principalDetails;
        //회원 가입
        if(!result){
            SocialUserRegister socialUserRegister = SocialUserRegister.fromKakao(kakaoProfile);
            principalDetails = new PrincipalDetails(userService.socialUserRegister(socialUserRegister));
        }else{
            principalDetails = userService.getPrincipalDetails(kakaoProfile.getEmail());
        }
        log.info("[카카오 로그인] {}", principalDetails);
        //로그인
        authService.loadUserDirectly(principalDetails, session);

        return "redirect:/board/home";
    }

}
