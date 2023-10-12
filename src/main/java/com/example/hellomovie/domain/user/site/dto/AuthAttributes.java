package com.example.hellomovie.domain.user.site.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthAttributes {
        private String kakaoApiKey;
        private String kakaoRedirectUrl;
}
