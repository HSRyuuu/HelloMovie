package com.example.hellomovie.domain.user.persist;

import com.example.hellomovie.domain.user.dto.RegisterUser;
import com.example.hellomovie.domain.user.type.RegisterType;
import com.example.hellomovie.domain.user.type.UserStatus;
import com.example.hellomovie.global.security.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    private String userId; //email
    private String password;

    private String name;
    private String nickname;

    private String accessToken;

    //이메일 인증 관련
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    @Enumerated(EnumType.STRING)
    private RegisterType registerType;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public User(String userId){
        this.userId = userId;
    }

    public static User registerUser(RegisterUser input) {
        String encPassword = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());

        return User.builder()
                .userId(input.getUserId())
                .password(encPassword)
                .name(input.getName())
                .nickname(input.getNickname())
                .accessToken("site-register")
                .emailAuthKey(UUID.randomUUID().toString())
                .registerType(RegisterType.SITE)
                .userStatus(UserStatus.EMAIL_AUTH_REQUIRED)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.USER));
        if(this.registerType.equals(RegisterType.ADMIN)){
            grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ADMIN));
        }
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
