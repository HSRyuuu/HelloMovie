package com.example.hellomovie.domain.post.service;

import com.example.hellomovie.domain.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    List<PostDto> list();
}
