package com.example.hellomovie.global.auth;

import com.example.hellomovie.global.kakao.KakaoApi;
import com.example.hellomovie.global.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;


@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final KakaoApi kakaoApi;

    @GetMapping("/login")
    public String login(Model model){
        AuthAttributes authAttributes = AuthAttributes.builder()
                .kakaoApiKey(kakaoApi.getApiKey())
                .kakaoRedirectUrl(kakaoApi.getREDIRECT_URI())
                .build();

        model.addAttribute("authObject", authAttributes);

        return "user/login";
    }

    @RequestMapping("/logout")
    public String kakaoLogout(@SessionAttribute HttpSession session){

        kakaoApi.kakaoLogout((String)session.getAttribute(SessionConst.ACCESS_TOKEN));
        session.removeAttribute(SessionConst.USER_ID);
        session.removeAttribute(SessionConst.ACCESS_TOKEN);

        return "redirect:/board/main";
    }


}
