package com.nisum.cartAndCheckout.exception;

public class UnauthorizedCartAccessException extends RuntimeException{
    public UnauthorizedCartAccessException (String message){
        super(message);
    }
}
