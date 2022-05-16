package com.readingisgood.Service.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class RigException extends RuntimeException{

    private HttpStatus response;
    public RigException(HttpStatus response, String message) {
        super(message);
        this.response = response;
    }
}
