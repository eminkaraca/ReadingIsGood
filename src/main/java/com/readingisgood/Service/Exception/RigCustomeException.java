package com.readingisgood.Service.Exception;

import org.springframework.http.HttpStatus;

public class RigCustomeException extends RigException {
    public RigCustomeException(HttpStatus response, String message) {
        super(response, message);
    }
}
