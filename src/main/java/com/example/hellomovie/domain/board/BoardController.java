package com.example.hellomovie.domain.board;

import com.example.hellomovie.domain.post.dto.PostDto;
import com.example.hellomovie.domain.post.service.PostService;
import com.example.hellomovie.domain.user.site.service.UserService;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/home")
    public String list(Model model,
                       @AuthenticationPrincipal PrincipalDetails user){
        if(Objects.isNull(user)){
            user = PrincipalDetails.empty();
        }

        log.info("user : {}", user);
        model.addAttribute("loginUser", user.getNickname());


        List<PostDto> list = postService.list();
        model.addAttribute("posts", list);

        return "board/home";
    }

    @GetMapping("/hot")
    public String hot(Model model,
                      @AuthenticationPrincipal PrincipalDetails user){
        if(Objects.isNull(user)){
            user = PrincipalDetails.empty();
        }
        model.addAttribute("loginUser", user.getNickname());

        return "board/hot";
    }

    @GetMapping("/movie")
    public String movie(Model model,
                        @AuthenticationPrincipal PrincipalDetails user){
        if(Objects.isNull(user)){
            user = PrincipalDetails.empty();
        }
        model.addAttribute("loginUser", user.getNickname());

        return "board/movie";
    }

}

