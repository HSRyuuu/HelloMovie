package com.example.hellomovie.global.auth.service;

import com.example.hellomovie.domain.user.site.dto.AuthAttributes;
import com.example.hellomovie.domain.user.site.dto.UserExistsResult;
import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.site.persist.UserRepository;
import com.example.hellomovie.domain.user.social.common.persist.SocialUser;
import com.example.hellomovie.domain.user.social.common.persist.SocialUserRepository;
import com.example.hellomovie.domain.user.social.kakao.api.KakaoApi;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.auth.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final KakaoApi kakaoApi;

    public AuthAttributes getAuthAttributes() {
        return AuthAttributes.builder()
                .kakaoApiKey(kakaoApi.getApiKey())
                .kakaoRedirectUrl(kakaoApi.getREDIRECT_URI())
                .build();
    }

    public UserExistsResult existsByUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()){
            return new UserExistsResult(true, UserType.SITE, user.get().getUserStatus());
        }
        Optional<SocialUser> socialUser = socialUserRepository.findByUserId(userId);
        if(socialUser.isEmpty()){
            return new UserExistsResult(false, null, null);
        }
        SocialUser findSocialUser = socialUser.get();
        return new UserExistsResult(true, findSocialUser.getUserType(), findSocialUser.getUserStatus());
    }

    public void addPrincipalDetailsAttributes(Model model, PrincipalDetails principalDetails){
        if(Objects.isNull(principalDetails)){
            model.addAttribute("loginUser", "");
        }else{
            model.addAttribute("loginUser", principalDetails.getNickname());
        }
    }

}
