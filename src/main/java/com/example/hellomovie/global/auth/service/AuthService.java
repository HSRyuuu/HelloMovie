package com.example.hellomovie.global.auth.service;

import com.example.hellomovie.domain.user.site.dto.AuthAttributes;
import com.example.hellomovie.domain.user.social.kakao.api.KakaoApi;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final KakaoApi kakaoApi;

    public AuthAttributes getAuthAttributes() {
        return AuthAttributes.builder()
                .kakaoApiKey(kakaoApi.getApiKey())
                .kakaoRedirectUrl(kakaoApi.getREDIRECT_URI())
                .build();
    }

    public void addPrincipalDetailsAttributes(Model model, PrincipalDetails principalDetails){
        if(Objects.isNull(principalDetails)){
            model.addAttribute("loginUser", "");
        }else{
            model.addAttribute("loginUser", principalDetails.getNickname());
        }
    }

}
