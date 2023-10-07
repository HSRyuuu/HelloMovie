package com.example.hellomovie.domain.post.service;

import com.example.hellomovie.domain.post.dto.PostDto;
import com.example.hellomovie.domain.post.persist.Post;
import com.example.hellomovie.domain.post.persist.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<PostDto> list() {
        List<Post> list = postRepository.findAll();
        return list.stream().map(post -> PostDto.fromEntity(post)).collect(Collectors.toList());
    }
}
