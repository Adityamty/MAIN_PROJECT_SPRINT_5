package com.orderservice.sprint4.exception;

public class OrderTransactionException extends RuntimeException {
    public OrderTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
