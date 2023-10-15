package com.example.hellomovie.global.auth.principal;

import com.example.hellomovie.domain.user.site.persist.User;
import com.example.hellomovie.global.auth.type.UserType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PrincipalDetails implements UserDetails{

    private User user;
    private String nickname;

    public PrincipalDetails(User user){
        this.user = user;
        this.nickname = user.getNickname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(this.user.getUserType().equals(UserType.ADMIN)){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserId();
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
