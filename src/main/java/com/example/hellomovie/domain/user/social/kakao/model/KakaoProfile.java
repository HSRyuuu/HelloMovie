package com.example.hellomovie.domain.user.social.kakao.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
public class KakaoProfile {
    private Integer id;
    private LocalDateTime connectedAt;
    private String email;
    private String nickname;

    public KakaoProfile(String jsonResponseBody){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonResponseBody);

        this.id = element.getAsJsonObject().get("id").getAsInt();

        String connected_at = element.getAsJsonObject().get("connected_at").getAsString();
        connected_at = connected_at.substring(0, connected_at.length() - 1);
        LocalDateTime connectDateTime = LocalDateTime.parse(connected_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.connectedAt = connectDateTime;

        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        this.nickname = properties.getAsJsonObject().get("nickname").getAsString();

        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
        this.email = kakaoAccount.getAsJsonObject().get("email").getAsString();
    }

}
