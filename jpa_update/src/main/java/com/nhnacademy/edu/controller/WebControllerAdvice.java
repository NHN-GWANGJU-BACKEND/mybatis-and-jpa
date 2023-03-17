package com.nhnacademy.edu.controller;

import com.nhnacademy.edu.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler(value = {
            NotExistUserId.class,
            NotFoundFileException.class,
            NotImageFileException.class,
            FailedQueryExecuteException.class,
            FileDownloadException.class,
            NotAllowAccessException.class,
            Exception.class})
    public String handleValidationException(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);

        return "error";
    }
}
