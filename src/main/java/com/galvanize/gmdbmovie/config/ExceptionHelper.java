package com.galvanize.gmdbmovie.config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleInvalidInputException(NoSuchElementException ex) {
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
