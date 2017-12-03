package com.panono.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.panono.exception.InvalidTimestampException;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Arguments not valid") 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException() {
    	// handler
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid timestamp") 
    @ExceptionHandler(InvalidTimestampException.class)
    public void handleInvalidTimestampException() {
    	// handler
    }
}
