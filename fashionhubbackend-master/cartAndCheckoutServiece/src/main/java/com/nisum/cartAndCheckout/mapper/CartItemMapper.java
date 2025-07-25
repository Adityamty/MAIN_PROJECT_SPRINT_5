package com.nisum.cartAndCheckout.mapper;

import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;

public class CartItemMapper {
    public static CartItem toCartItem(CartItemRequestDTO dto, ShoppingCart cart) {
        return CartItem.builder()
                .cart(cart)
                .productId(dto.getProductId())
                .sku(dto.getSku())
                .size(dto.getSize())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .discount(dto.getDiscount())
                .finalPrice(dto.getFinalPrice())
                .isSavedForLater(false)
                .build();
    }
}
