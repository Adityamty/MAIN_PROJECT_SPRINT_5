package com.nisum.cartAndCheckout.exception;

public class DuplicateCartItemException extends RuntimeException {
    public DuplicateCartItemException(String message) {
        super(message);
    }
}
