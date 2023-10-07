package com.example.hellomovie.domain.post.dto;

import com.example.hellomovie.domain.post.persist.Post;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private Long id;
    private String nickname;
    private String title;
    private String text;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String fileUrl;

    public static PostDto fromEntity(Post post){
        return PostDto.builder()
                .id(post.getId())
                .nickname(post.getNickname())
                .title(post.getTitle())
                .views(post.getViews())
                .text(post.getText())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .fileUrl(post.getFileUrl())
                .build();
    }

}
