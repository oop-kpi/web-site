package com.oopwebsite.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class IllegalAccessException extends RuntimeException {
    public IllegalAccessException(String message){
        super(message);
    }
}
