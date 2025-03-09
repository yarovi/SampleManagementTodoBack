package org.yasmani.io.todomanagerapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotfountException extends RuntimeException {
    public ResourceNotfountException(String message) {
        super(message);
    }
}
