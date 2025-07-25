package com.nisum.cartAndCheckout.exception;

public class PromoServiceUnavailableException extends RuntimeException {
    public PromoServiceUnavailableException(String message) {
        super(message);
    }

    public PromoServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
