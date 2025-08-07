package com.bootcamp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}