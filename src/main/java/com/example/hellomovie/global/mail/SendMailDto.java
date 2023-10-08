package com.example.hellomovie.global.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMailDto {
    private String email;
    private String userName;
    private String authKey;
}
