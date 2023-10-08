package com.example.hellomovie.global.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public String handleException(MyException e, Model model){
        ErrorCode errorCode = e.getErrorCode();
        int statusCode = errorCode.getStatusCode();
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("msg", errorCode.getDescription());
        if(statusCode >= 400 && statusCode < 500){
            return "error/4xx";
        }else{
            return "error/5xx";
        }
    }
}
