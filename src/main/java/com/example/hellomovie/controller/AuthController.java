package com.example.hellomovie.controller;

import com.example.hellomovie.global.kakao.KakaoApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final KakaoApi kakaoApi;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("key", kakaoApi.getApiKey());
        model.addAttribute("redirectUri", kakaoApi.getREDIRECT_URI());
        return "auth/login";
    }


}
