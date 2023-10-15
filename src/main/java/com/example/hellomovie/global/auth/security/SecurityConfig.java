package com.example.hellomovie.global.auth.security;

import com.example.hellomovie.global.auth.security.errorhandle.LoginSuccessHandler;
import com.example.hellomovie.global.auth.security.errorhandle.UserAuthenticationFailureHandler;
import com.example.hellomovie.global.auth.service.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailsService principalDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Bean
    LoginSuccessHandler getSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        //페이지 접근 관련
        http.authorizeRequests()
                //인증 요구
                .antMatchers("/user/**").authenticated()
                //ADMIN만 접근 가능
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN");

        //접근 허용
        http.authorizeRequests()
                .antMatchers("/", "/login/**", "/register/**", "/board/home")
                .permitAll();

        //로그인
        http.formLogin()
                .loginPage("/login-page")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/login")
                .failureUrl("/login-page")
                .failureHandler(getFailureHandler())
                .successHandler(getSuccessHandler())
                .permitAll();

        //로그아웃
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/board/home");

        //접근 거부 시 에러 페이지 설정
        http.exceptionHandling()
                .accessDeniedPage("/error/denied");

        super.configure(http);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/files/**", "/images/**");
        super.configure(web);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());
    }
}
