package com.nisum.cartAndCheckout.validation;

import java.util.List;

public class PromoValidator {

    public static void validateProductIds(List<Integer> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Product ID list cannot be empty.");
        }
    }
}
