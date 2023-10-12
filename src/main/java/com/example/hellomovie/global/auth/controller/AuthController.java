package com.example.hellomovie.global.auth.controller;

import com.example.hellomovie.domain.user.social.kakao.api.KakaoApi;
import com.example.hellomovie.global.auth.dto.LoginInput;
import com.example.hellomovie.global.auth.dto.LoginResult;
import com.example.hellomovie.global.auth.jwt.TokenProvider;
import com.example.hellomovie.global.auth.service.AuthService;
import com.example.hellomovie.domain.user.site.dto.AuthAttributes;
import com.example.hellomovie.global.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    /**
     * 일반 유저 로그인
     */
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("input", new LoginInput());
        model.addAttribute("authObject", authService.getAuthAttributes());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("input") LoginInput input, BindingResult bindingResult,
                        HttpSession session, Model model){
        LoginResult loginResult = authService.authenticateUser(input); //로그인 결과
        //Id가 존재하지 않는 유저인 경우
        if(!loginResult.isUserExistYn()){
            model.addAttribute("authObject", authService.getAuthAttributes());
            bindingResult.rejectValue("userId", "user doesn't exist", "존재하지 않는 회원입니다.");
            return "user/login";
        }else if(!loginResult.isPasswordCorrectYn()){
            model.addAttribute("authObject", authService.getAuthAttributes());
            bindingResult.rejectValue("password", "password incorrect", "비밀번호가 틀립니다.");
            return "user/login";
        }

        String token = tokenProvider.generateToken(loginResult.getUserId(), loginResult.getRoles());
        session.setAttribute("Authorization", "Bearer " + token);

        return "redirect:/board/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        return "redirect:/board/home";
    }

}
