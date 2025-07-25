
package com.orderservice.sprint4.exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(Integer orderId) {
        super("Invoice not found for Order ID: " + orderId);
    }
}
