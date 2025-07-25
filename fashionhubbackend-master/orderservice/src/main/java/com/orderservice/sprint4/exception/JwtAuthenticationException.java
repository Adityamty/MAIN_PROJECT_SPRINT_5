package com.orderservice.sprint4.exception;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message, RuntimeException e) {
        super(message);
    }
}
