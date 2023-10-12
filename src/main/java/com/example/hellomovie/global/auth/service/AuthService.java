package com.example.hellomovie.global.auth.service;

import com.example.hellomovie.domain.user.site.dto.AuthAttributes;
import com.example.hellomovie.domain.user.site.dto.UserExistsResult;
import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.domain.user.site.persist.UserRepository;
import com.example.hellomovie.domain.user.social.kakao.api.KakaoApi;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.auth.type.UserStatus;
import com.example.hellomovie.domain.user.social.common.persist.SocialUser;
import com.example.hellomovie.domain.user.social.common.persist.SocialUserRepository;
import com.example.hellomovie.global.auth.dto.LoginInput;
import com.example.hellomovie.global.auth.dto.LoginResult;
import com.example.hellomovie.global.auth.type.UserType;
import com.example.hellomovie.global.exception.ErrorCode;
import com.example.hellomovie.global.exception.OpenException;
import com.example.hellomovie.global.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final KakaoApi kakaoApi;
    /**
     * 소셜 로그인을 위한 model attributes반환
     */
    public AuthAttributes getAuthAttributes(){
        return AuthAttributes.builder()
                .kakaoApiKey(kakaoApi.getApiKey())
                .kakaoRedirectUrl(kakaoApi.getREDIRECT_URI())
                .build();
    }
    /**
     * Site User auth
     */
    public LoginResult authenticateUser(LoginInput input) {
        Optional<User> optionalUser = userRepository.findByUserId(input.getUserId());
        if(optionalUser.isEmpty()){
            LoginResult loginResult = new LoginResult();
            loginResult.setUserExistYn(false);
            return loginResult;
        }
        User user = optionalUser.get();
        if(!PasswordUtils.equalsPlainTextAndHashed(input.getPassword(), user.getPassword())){
            LoginResult loginResult = new LoginResult();
            loginResult.setUserExistYn(true);
            loginResult.setPasswordCorrectYn(false);
            return loginResult;
        }
        return LoginResult.fromEntity(user);
    }



    public UserExistsResult existsByUserId(String userId){
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserExistsResult existsResult = this.existsByUserId(username);
        boolean result = existsResult.isExistYn();
        UserType userType = existsResult.getUserType();

        if(!result){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }else if(userType.equals(UserType.SITE)){
            User user = userRepository.findByUserId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));
            if(user.getUserStatus().equals(UserStatus.EMAIL_AUTH_REQUIRED)){
                throw new OpenException(ErrorCode.EMAIL_AUTH_REQUIRED);
            }
            if(user.getUserStatus().equals(UserStatus.STOP)){
                throw new OpenException(ErrorCode.USER_STOPPED);
            }
            log.info("loadUserByUsername : {}", user);
            return User.toPrincipalDetails(user);
        }else{
            SocialUser socialUser = socialUserRepository.findByUserId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));
            if(socialUser.getUserStatus().equals(UserStatus.STOP)){
                throw new OpenException(ErrorCode.USER_STOPPED);
            }
            log.info("loadUserByUsername : {}", socialUser);
            return SocialUser.toPrincipalDetails(socialUser);
        }
    }


}
