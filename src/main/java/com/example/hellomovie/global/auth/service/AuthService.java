package com.example.hellomovie.global.auth.service;

import com.example.hellomovie.domain.user.site.dto.AuthAttributes;
import com.example.hellomovie.global.auth.component.AuthConstUtil;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class AuthService {

    /**
     * loginForm의 소셜 로그인 기능을 위한 속성
     * @return 필요한 속성이 담긴 객체(AuthAttributes)를 반환
     */
    public AuthAttributes getAuthAttributes() {
        return AuthAttributes.builder()
                .kakaoApiKey(AuthConstUtil.KAKAO_API_KEY)
                .kakaoRedirectUrl(AuthConstUtil.KAKAO_REDIRECT_URL)
                .build();
    }

    /**
     * @AuthenticationPrincipal로 얻은 principalDetails 객체를 판단하여
     * model에 "loginUser"란 이름으로 nickname을 담음
     */
    public void addPrincipalDetailsAttributes(Model model, PrincipalDetails principalDetails){
        if(Objects.isNull(principalDetails)){
            model.addAttribute("loginUser", "");
        }else{
            model.addAttribute("loginUser", principalDetails.getNickname());
        }
    }

    /**
     * 소셜 로그인 시 직접 PrincipalDetails를 SpringSecurityContext에 담음
     */
    public void loadUserDirectly(PrincipalDetails principalDetails, HttpSession session){
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

}
