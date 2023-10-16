package com.example.hellomovie.domain.board;

import com.example.hellomovie.domain.post.dto.PostDto;
import com.example.hellomovie.domain.post.service.PostService;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.auth.service.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;
    private final AuthUtils authUtils;

    @GetMapping("/home")
    public String list(Model model,
                       @AuthenticationPrincipal PrincipalDetails principalDetails){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        authUtils.addPrincipalDetailsAttributes(model, principalDetails);

        List<PostDto> list = postService.list();
        model.addAttribute("posts", list);

        return "board/home";
    }

    @GetMapping("/hot")
    public String hot(Model model,
                      @AuthenticationPrincipal PrincipalDetails principalDetails){
        authUtils.addPrincipalDetailsAttributes(model, principalDetails);


        return "board/hot";
    }

    @GetMapping("/movie")
    public String movie(Model model,
                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        authUtils.addPrincipalDetailsAttributes(model, principalDetails);

        return "board/movie";
    }

}

