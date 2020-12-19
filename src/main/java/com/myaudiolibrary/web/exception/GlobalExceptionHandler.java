package com.myaudiolibrary.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException e){
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.BAD_REQUEST);
        return modelAndView;
    }

    @ExceptionHandler(EntityExistsException.class)
    public ModelAndView handleEntityExistsException(EntityExistsException e){
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.CONFLICT);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.CONFLICT);
        return modelAndView;
    }

/*    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return entityNotFoundException.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException
    ){
        return illegalArgumentException.getMessage();
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEntityExistsException(
            EntityExistsException entityExistsException
    ){
        return entityExistsException.getMessage();
    }*/
}
