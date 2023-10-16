package com.example.hellomovie.global.auth.controller;

import com.example.hellomovie.global.auth.dto.LoginInput;
import com.example.hellomovie.global.auth.service.AuthUtils;
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

    private final AuthUtils authUtils;

    /**
     * 일반 유저 로그인
     */
    @GetMapping("/login-page")
    public String loginForm(Model model){
        model.addAttribute("input", new LoginInput());
        model.addAttribute("authObject", authUtils.getAuthAttributes());
        return "user/login";
    }

    @RequestMapping("/login")
    public String loginProcessing(Model model){
        model.addAttribute("input", new LoginInput());
        model.addAttribute("authObject", authUtils.getAuthAttributes());
        return "user/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        return "redirect:/board/home";
    }

}
