package com.nisum.cartAndCheckout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCartItemSizeDto {
    private String message;
    private int stockQuantity;
    private double unitPrice;
    private double discount;
    private double finalPrice;
}