package com.bogovich.recipe.controllers;

import com.bogovich.recipe.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex){
        log.error(ex.getMessage());
        return new ModelAndView("errorPage")
                .addObject("exception", ex)
                .addObject("errorCode", 404)
                .addObject("errorTitle", "404 NOT FOUND");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception ex){
        log.error(ex.getMessage());
        return new ModelAndView("errorPage")
                .addObject("exception", ex)
                .addObject("errorCode", 400)
                .addObject("errorTitle", "400 BAD FORMAT");
    }
}
