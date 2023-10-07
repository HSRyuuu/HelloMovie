package com.example.hellomovie.domain.post.persist;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String title;
    private String text;
    private Long views;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String fileUrl;

}
