package com.myaudiolibrary.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Everytime we throw an exception, it's handled with a handle method
    //ERROR404
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return entityNotFoundException.getMessage();
    }

    //ERROR400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException
    ){
        return illegalArgumentException.getMessage();
    }

    //ERROR409
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEntityExistsException(
            EntityExistsException entityExistsException
    ){
        return entityExistsException.getMessage();
    }
}
