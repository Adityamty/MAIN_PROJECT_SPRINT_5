package com.orderservice.sprint4.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Integer orderId) {
        super("Order with ID " + orderId + " not found");
    }
}

