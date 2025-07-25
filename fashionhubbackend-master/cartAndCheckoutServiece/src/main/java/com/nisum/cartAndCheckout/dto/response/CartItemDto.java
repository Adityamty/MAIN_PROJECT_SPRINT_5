package com.nisum.cartAndCheckout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;
    private String productName;
    private String size;
    private List<String> availableSizes;
    private int cartQuantity;
    private int stockQuantity;
    private double unitPrice;
    private double discount;
    private double finalPrice;
    private String imageurl;
}