package com.example.hellomovie.global.auth.controller;

import com.example.hellomovie.global.auth.dto.LoginInput;
import com.example.hellomovie.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    /**
     * 일반 유저 로그인
     */
    @GetMapping("/login-page")
    public String loginForm(Model model){
        model.addAttribute("input", new LoginInput());
        model.addAttribute("authObject", authService.getAuthAttributes());
        return "user/login";
    }

    @RequestMapping("/login")
    public String loginProcessing(Model model){
        model.addAttribute("input", new LoginInput());
        model.addAttribute("authObject", authService.getAuthAttributes());
        return "user/login";
    }
//
//    @PostMapping("/login")
//    public String login(@Validated @ModelAttribute("input") LoginInput input, BindingResult bindingResult,
//                        Model model){
//        LoginResult loginResult = authService.authenticateUser(input); //로그인 결과
//        //Id가 존재하지 않는 유저인 경우
//        if(!loginResult.isUserExistYn()){
//            model.addAttribute("authObject", authService.getAuthAttributes());
//            bindingResult.rejectValue("userId", "user doesn't exist", "존재하지 않는 회원입니다.");
//            return "user/login";
//        }else if(!loginResult.isPasswordCorrectYn()){
//            model.addAttribute("authObject", authService.getAuthAttributes());
//            bindingResult.rejectValue("password", "password incorrect", "비밀번호가 틀립니다.");
//            return "user/login";
//        }
//
//        return "redirect:/board/home";
//    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        return "redirect:/board/home";
    }

}
