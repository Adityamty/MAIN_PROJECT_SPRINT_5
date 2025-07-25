package com.nisum.cartAndCheckout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCartItemDto {
    private String message;
    private int stockQuantity;
}