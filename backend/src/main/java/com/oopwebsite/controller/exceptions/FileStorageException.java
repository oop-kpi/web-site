package com.oopwebsite.controller.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "File storage exception!")
public class FileStorageException extends RuntimeException {
    public FileStorageException(String s){
        super(s);
    }
}
