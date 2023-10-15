package com.example.hellomovie.global.auth.type;

public enum UserType {
    SITE, KAKAO, GOOGLE, ADMIN;

    public static UserType getType(String type){
        if(type.equals("google")){
            return UserType.GOOGLE;
        }else if(type.equals("kakao")){
            return UserType.KAKAO;
        }else{
            return null;
        }
    }
}
