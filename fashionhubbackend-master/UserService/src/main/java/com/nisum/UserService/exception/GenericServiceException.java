package com.nisum.UserService.exception;

public class GenericServiceException extends RuntimeException {
    public GenericServiceException(String message) {
        super(message);
    }

    public GenericServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}