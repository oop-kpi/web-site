package com.oopwebsite.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NoSuchElementException extends RuntimeException {
    public     NoSuchElementException(String msg){
        super(msg);
    }
}
