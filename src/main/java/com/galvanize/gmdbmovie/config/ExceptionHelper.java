package com.galvanize.gmdbmovie.config;


import com.galvanize.gmdbmovie.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleInvalidInputException(NoSuchElementException ex) {
        Response response = new Response();
        response.setMessage(ex.getMessage());
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }
}
