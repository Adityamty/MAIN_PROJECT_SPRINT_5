package com.nisum.cartAndCheckout.validation;

import com.nisum.cartAndCheckout.dto.response.InventoryAvailabilityResponseDTO;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.exception.InventoryException;

import static com.nisum.cartAndCheckout.constants.AppConstants.*;

import java.util.List;

public class InventoryValidator {

    public static void validateInventory(List<CartItem> cartItems,
                                         List<InventoryAvailabilityResponseDTO> inventoryAvailability) {

        for (CartItem item : cartItems) {
            InventoryAvailabilityResponseDTO inventory = inventoryAvailability.stream()
                    .filter(i -> i.getSku().equals(item.getSku()))
                    .findFirst()
                    .orElseThrow(() -> new InventoryException(SKU_NOT_FOUND + item.getSku(), null));

            if (inventory.getAvailableQuantity() < item.getQuantity()) {
                throw new InventoryException(INSUFFICIENT_QUANTITY + item.getSku(), null);
            }
        }
    }
}
