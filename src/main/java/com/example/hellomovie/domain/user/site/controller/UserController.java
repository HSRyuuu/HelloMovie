package com.example.hellomovie.domain.user.site.controller;

import com.example.hellomovie.domain.user.site.dto.RegisterUserResult;
import com.example.hellomovie.domain.user.site.model.RegisterUser;
import com.example.hellomovie.domain.user.site.dto.UserDto;
import com.example.hellomovie.domain.user.site.service.UserService;
import com.example.hellomovie.global.auth.principal.PrincipalDetails;
import com.example.hellomovie.global.mail.MailComponents;
import com.example.hellomovie.global.mail.SendMailDto;
import com.example.hellomovie.global.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final MailComponents mailComponents;


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
        //회원 가입
        RegisterUserResult result = userService.register(input);

        //회원가입 성공 시
        if(result.isSuccessYn()){
            mailComponents.sendMailForRegister(
                    new SendMailDto(result.getUserId(),
                            result.getNickname(),
                                result.getEmailAuthKey()));
        }
        model.addAttribute("result", result.isSuccessYn());
        return "user/register_result";
    }

    @GetMapping("/register/social")
    public String registerSocialUser(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     Model model){
        model.addAttribute("loginNickname", principalDetails.getNickname());
        model.addAttribute("user", principalDetails.getUser());

        return "user/register_result_social";
    }

    @GetMapping("/auth/email-auth")
    public String emailAuth(@RequestParam String key, Model model){
        boolean result = userService.emailAuth(key);
        model.addAttribute("result", result);

        return "user/email_auth_result";
    }
}
