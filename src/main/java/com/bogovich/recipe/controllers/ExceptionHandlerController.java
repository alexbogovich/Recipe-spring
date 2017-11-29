package com.bogovich.recipe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.thymeleaf.exceptions.TemplateInputException;

//@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TemplateInputException.class)
    public String handleNotFound(Exception ex, Model model) {
//        log.error(ex.getMessage());
        model.addAttribute("exception", ex)
                .addAttribute("errorCode", 404)
                .addAttribute("errorTitle", "404 NOT FOUND");
        return "errorPage";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({WebExchangeBindException.class, IllegalArgumentException.class})
    public String handleNumberFormat(Exception ex, Model model) {
//        log.error(ex.getMessage());
        model.addAttribute("exception", ex)
                .addAttribute("errorCode", 400)
                .addAttribute("errorTitle", "400 BAD FORMAT");
        return "errorPage";
    }
}
