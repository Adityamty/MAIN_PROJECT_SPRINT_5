package com.nisum.cartAndCheckout.exception;

public class InventoryMappingException extends RuntimeException {
    public InventoryMappingException(String message) {
        super(message);
    }

    public InventoryMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
