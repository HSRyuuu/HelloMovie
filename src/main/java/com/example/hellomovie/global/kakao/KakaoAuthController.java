package com.example.hellomovie.global.kakao;

import com.example.hellomovie.global.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
public class KakaoAuthController {

    private final KakaoApi kakaoApi;

    @RequestMapping("/kakaologin")
    public String kakaoLogin(@RequestParam String code, HttpSession session, Model model){
        // 1. 인가 코드 받기
        String accessToken = kakaoApi.getAccessToken(code);
        // 2. 토큰 받기
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
        log.info("login info : {}", userInfo.toString());

        if(userInfo.get("email") != null){
            session.setAttribute(SessionConst.USER_ID, userInfo.get("email"));
            session.setAttribute(SessionConst.ACCESS_TOKEN, accessToken);
        }

        model.addAttribute("userId", userInfo.get("email"));
        return "redirect:/main";
    }

    @RequestMapping("/kakaologout")
    public String kakaoLogout(HttpSession session){
        kakaoApi.kakaoLogout((String)session.getAttribute(SessionConst.ACCESS_TOKEN));
        session.removeAttribute(SessionConst.USER_ID);
        session.removeAttribute(SessionConst.ACCESS_TOKEN);

        return "redirect:/main";
    }
}
