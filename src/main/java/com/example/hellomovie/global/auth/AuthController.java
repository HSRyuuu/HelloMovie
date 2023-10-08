package com.example.hellomovie.global.auth;

import com.example.hellomovie.global.kakao.KakaoApi;
import com.example.hellomovie.global.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;


@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {
    private final KakaoApi kakaoApi;

    /**
     * 로그인 페이지
     */
    @GetMapping("/login-page")
    public String loginForm(Model model){
        addAuthorities(model);
        return "user/login";
    }


    /**
     * security를 위한 login url
     */
    @RequestMapping("/login")
    public String login(Model model){
        addAuthorities(model);
        return "user/login";
    }

    private void addAuthorities(Model model){
        AuthAttributes authAttributes = AuthAttributes.builder()
                .kakaoApiKey(kakaoApi.getApiKey())
                .kakaoRedirectUrl(kakaoApi.getREDIRECT_URI())
                .build();

        model.addAttribute("authObject", authAttributes);
    }


    @RequestMapping("/logout")
    public String kakaoLogout(@SessionAttribute HttpSession session){

        kakaoApi.kakaoLogout((String)session.getAttribute(SessionConst.ACCESS_TOKEN));
        session.removeAttribute(SessionConst.USER_ID);
        session.removeAttribute(SessionConst.ACCESS_TOKEN);

        return "redirect:/board/main";
    }


}
