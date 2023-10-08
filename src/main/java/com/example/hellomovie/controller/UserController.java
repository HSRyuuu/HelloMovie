package com.example.hellomovie.controller;

import com.example.hellomovie.domain.user.dto.RegisterUser;
import com.example.hellomovie.domain.user.service.UserService;
import com.example.hellomovie.global.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("userInput", new RegisterUser());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("userInput")RegisterUser input, BindingResult bindingResult,
                           Model model){
        //비밀번호 일치 확인
        if(!PasswordUtils.equalsPlainText(input.getPassword(), input.getPasswordCheck())){
            bindingResult.rejectValue("password", "passwordIncorrect", "비밀번호가 일치하지 않습니다.");
            return "user/register";
        }
        boolean result = userService.register(input);

        model.addAttribute("result", result);
        return "user/register_result";
    }

    @GetMapping("/auth/email-auth")
    public String emailAuth(@RequestParam String key, Model model){
        boolean result = userService.emailAuth(key);
        model.addAttribute("result", result);

        return "user/email_auth_result";
    }
}
