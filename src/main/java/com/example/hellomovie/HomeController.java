package com.example.hellomovie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/main")
    public String list(){
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
