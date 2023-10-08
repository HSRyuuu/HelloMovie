package com.example.hellomovie.controller;

import com.example.hellomovie.domain.post.dto.PostDto;
import com.example.hellomovie.domain.post.service.PostService;
import com.example.hellomovie.global.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;

    @GetMapping("/home")
    public String list(Model model,
                       HttpSession session){
        String userId = (String)session.getAttribute(SessionConst.USER_ID);
        log.info("userId : {}", userId);
        model.addAttribute("loginId", userId);
        List<PostDto> list = postService.list();
        model.addAttribute("posts", list);

        return "board/home";
    }
    @GetMapping("/hot")
    public String hot(Model model,HttpSession session){
        String userId = (String)session.getAttribute(SessionConst.USER_ID);
        log.info("userId : {}", userId);
        model.addAttribute("loginId", userId);

        return "board/hot";
    }

    @GetMapping("/movie")
    public String movie(Model model,HttpSession session){
        String userId = (String)session.getAttribute(SessionConst.USER_ID);
        log.info("userId : {}", userId);
        model.addAttribute("loginId", userId);

        return "board/movie";
    }
}

