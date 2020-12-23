package com.myaudiolibrary.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Les exceptions sont écrites différement en Thymeleaf, ici on redirige l'utilisateur sur un template spécifique
    //ou l'on affiche le code d'erreur et le message que l'on a associé a ce dernier
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e){
        //Ici on renvoie un objet ModelAndView, cet objet prend en paramètre le nom du template et le status d'erreur
        ModelAndView modelAndView = new ModelAndView("error", HttpStatus.NOT_FOUND);
        //Pareillement au ModelMap on passe des object avec notre modelAndView , la on passe notre message d'erreur et le
        //code d'erreur HTTP
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

}
