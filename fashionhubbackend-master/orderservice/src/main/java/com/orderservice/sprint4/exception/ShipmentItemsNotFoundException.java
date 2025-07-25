
package com.orderservice.sprint4.exception;

public class ShipmentItemsNotFoundException extends RuntimeException {
    public ShipmentItemsNotFoundException(Integer orderId) {
        super("No shipment items found for Order ID: " + orderId);
    }
}
