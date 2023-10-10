package com.example.hellomovie.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException e){
        log.error("ErrorOccuredAt: {}, statusCode: {}, msg: {}",
                e.getOccurredAt(),
                e.getErrorCode().getStatusCode(),
                e.getErrorCode().getDescription());
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
    public String handleRuntimeException(RuntimeException e){
        log.error(e.getMessage());
        return "error/error";
    }
}
