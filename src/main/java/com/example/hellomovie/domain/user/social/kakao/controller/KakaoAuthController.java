package com.example.hellomovie.domain.user.social.kakao.controller;

import com.example.hellomovie.domain.user.social.common.persist.SocialUser;
import com.example.hellomovie.domain.user.social.common.service.SocialUserService;
import com.example.hellomovie.domain.user.social.kakao.api.KakaoApi;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.auth.service.AuthService;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.global.auth.type.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping()
@Controller
public class KakaoAuthController {

    private final KakaoApi kakaoApi;

    @RequestMapping("/login/oauth2/code/kakao")
    public @ResponseBody String kakaoLogin(@RequestParam String code, RedirectAttributes redirectAttributes){
        // 1. 인가 코드 받기
        String accessToken = kakaoApi.getAccessToken(code);
        // 2. 토큰 받기
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String email = (String)userInfo.get("email");
        log.info("[kakao login] email : {}", email);

        return "[kakao login] email : " + email;
    }

//    @GetMapping("/register")
//    public String registerForm(@RequestParam String email, Model model){
//        SocialUserDto findUser = socialUserService.getByUserId(email);
//        RegisterUser input = new RegisterUser();
//        input.setUserId(findUser.getUserId());
//        input.setName(findUser.getName());
//        model.addAttribute("userInput", input);
//        return "user/register_kakao";
//    }
//
//    @PostMapping("/register")
//    public String register(@Validated @ModelAttribute RegisterUser input, BindingResult bindingResult,
//                           Model model){
//        if(socialUserService.existsByNickname(input.getNickname())){
//            bindingResult.rejectValue("nickname", "nickname already exists", "이미 존재하는 닉네임입니다.");
//            return "user/register_kakao";
//        }
//        SocialUserDto socialUserDto = socialUserService.registerFinally(input);
//        if(Objects.isNull(socialUserDto)){
//            throw new OpenException(ErrorCode.UNKNOWN_ERROR);
//        }
//        model.addAttribute("user", socialUserDto);
//        return "user/register_result_social";
//    }

}
