package com.readingisgood.Service.Exception;

import org.springframework.http.HttpStatus;

public class PersistedBeforeException extends RigException {
    public PersistedBeforeException(HttpStatus response, String message) {
        super(response, message);
    }
}
