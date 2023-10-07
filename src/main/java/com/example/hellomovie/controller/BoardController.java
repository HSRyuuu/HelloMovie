package com.example.hellomovie.controller;

import com.example.hellomovie.domain.post.dto.PostDto;
import com.example.hellomovie.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class BoardController {

    private final PostService postService;

    @GetMapping("/main")
    public String list(Model model){
        List<PostDto> list = postService.list();
        model.addAttribute("posts", list);
        return "board/main";
    }
    @GetMapping("/hot")
    public String hot(){
        return "board/hot";
    }

    @GetMapping("/movie")
    public String movie(){
        return "board/movie";
    }
}

