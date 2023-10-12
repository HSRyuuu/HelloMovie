package com.example.hellomovie.global.auth.jwt;


import com.example.hellomovie.global.exception.CustomException;
import com.example.hellomovie.global.exception.ErrorCode;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }catch (JwtException e) {
            String message = e.getMessage();
            if (message.equals(ErrorCode.JWT_TOKEN_WRONG_TYPE.getDescription())) {
                throw new CustomException(ErrorCode.JWT_TOKEN_WRONG_TYPE, "JWT_TOKEN_WRONG_TYPE");
            } else if (message.equals(ErrorCode.TOKEN_TIME_OUT.getDescription())) {
                throw new CustomException(ErrorCode.TOKEN_TIME_OUT, "TOKEN_TIME_OUT");
            }
        }
    }

}
