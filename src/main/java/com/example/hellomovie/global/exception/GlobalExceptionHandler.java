package com.example.hellomovie.global.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String handleMyException(CustomException e, Model model){
        ErrorCode errorCode = e.getErrorCode();
        int statusCode = errorCode.getStatusCode();
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("msg", errorCode.getDescription());
        return "error/error";
    }

    @ExceptionHandler(OpenException.class)
    public String handleOpenException(OpenException e, Model model){
        ErrorCode errorCode = e.getErrorCode();
        int statusCode = errorCode.getStatusCode();
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("msg", errorCode.getDescription());
        return "error/open_error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, Model model){
        model.addAttribute("msg", e.getMessage());
        return "error/error";
    }
}
