package com.example.hellomovie.global.auth.principal;

import com.example.hellomovie.global.auth.type.UserType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PrincipalDetails implements UserDetails{

    private String userId;
    private String password;
    private String name;
    private String nickname;
    private UserType userType;

    public static PrincipalDetails empty(){
        PrincipalDetails principalDetails = new PrincipalDetails();
        principalDetails.setName("!none");
        principalDetails.setNickname("!none");
        principalDetails.setUserId("!none");
        principalDetails.setPassword("!none");
        return principalDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(userType.equals(UserType.ADMIN)){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    //계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //계정 정보 변경 필요 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}
