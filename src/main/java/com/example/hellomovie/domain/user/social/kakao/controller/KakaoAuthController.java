package com.example.hellomovie.domain.user.social.kakao.controller;

import com.example.hellomovie.domain.user.site.dto.RegisterUser;
import com.example.hellomovie.domain.user.social.common.dto.SocialUserDto;
import com.example.hellomovie.domain.user.social.kakao.api.KakaoApi;
import com.example.hellomovie.domain.user.social.kakao.dto.SocialUserInfo;
import com.example.hellomovie.domain.user.social.common.service.SocialUserService;
import com.example.hellomovie.domain.user.site.dto.UserExistsResult;
import com.example.hellomovie.global.auth.service.AuthService;
import com.example.hellomovie.global.auth.type.UserType;
import com.example.hellomovie.global.exception.ErrorCode;
import com.example.hellomovie.global.exception.OpenException;
import com.example.hellomovie.global.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
@Controller
public class KakaoAuthController {

    private final KakaoApi kakaoApi;
    private final AuthService authService;
    private final SocialUserService socialUserService;

    @RequestMapping("/login")
    public String kakaoLogin(@RequestParam String code, RedirectAttributes redirectAttributes){
        // 1. 인가 코드 받기
        String accessToken = kakaoApi.getAccessToken(code);
        // 2. 토큰 받기
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String email = (String)userInfo.get("email");
        SocialUserInfo socialUserInfo = SocialUserInfo.builder()
                .email(email)
                .nickname((String)userInfo.get("nickname"))
                .accessToken(accessToken)
                .userType(UserType.KAKAO).build();

        log.info("kakao login user : {}", socialUserInfo);

        UserExistsResult existsResult = authService.existsByUserId(socialUserInfo.getEmail());

        if(existsResult.isExistYn() && existsResult.getUserType().equals(socialUserInfo.getUserType())){
            //카카오 소셜 계정이 존재할 때 - 로그인

        }
        else if(existsResult.isExistYn()){
            //소셜 계정은 존재하지만 카카오 로그인이 아닐 때
            throw new OpenException(ErrorCode.SOCIAL_USER_ALREADY_EXIST);
        }else{
            //소셜 계정이 존재하지 않을 떄 : 회원가입
            socialUserService.registerTemporarily(socialUserInfo);
            redirectAttributes.addAttribute("email", socialUserInfo.getEmail());
            return "redirect:/auth/kakao/register";
        }
        return "redirect:/board/home";
    }

    @GetMapping("/register")
    public String registerForm(@RequestParam String email, Model model){
        SocialUserDto findUser = socialUserService.getByUserId(email);
        RegisterUser input = new RegisterUser();
        input.setUserId(findUser.getUserId());
        input.setName(findUser.getName());
        model.addAttribute("userInput", input);
        return "user/register_kakao";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute RegisterUser input, BindingResult bindingResult,
                           Model model){
        if(socialUserService.existsByNickname(input.getNickname())){
            bindingResult.rejectValue("nickname", "nickname already exists", "이미 존재하는 닉네임입니다.");
            return "user/register_kakao";
        }
        SocialUserDto socialUserDto = socialUserService.registerFinally(input);
        if(Objects.isNull(socialUserDto)){
            throw new OpenException(ErrorCode.UNKNOWN_ERROR);
        }
        model.addAttribute("user", socialUserDto);
        return "user/register_result_social";
    }

}
