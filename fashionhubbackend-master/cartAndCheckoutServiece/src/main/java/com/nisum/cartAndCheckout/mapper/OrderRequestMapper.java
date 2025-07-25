package com.nisum.cartAndCheckout.mapper;

import com.nisum.cartAndCheckout.dto.request.OrderItemRequestDTO;
import com.nisum.cartAndCheckout.dto.request.OrderRequestDTO;
import com.nisum.cartAndCheckout.entity.CartItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRequestMapper {
    public static OrderRequestDTO toOrderRequestDTO(Integer userId, List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart items are null or empty");
        }

        List<OrderItemRequestDTO> orderItemRequestDTOS = cartItems.stream()
                .peek(item -> {
                    if (item == null) {
                        throw new NullPointerException("CartItem is null");
                    }
                    if (item.getSku() == null) {
                        throw new NullPointerException("CartItem SKU is null");
                    }
                })
                .map(item -> OrderItemRequestDTO.builder()
                        .productId(item.getProductId())
                        .sku(item.getSku())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .finalPrice(item.getFinalPrice())
                        .size(item.getSize())
                        .status("Confirmed")
                        .sellerId(301)
                        .build())
                .collect(Collectors.toList());

        BigDecimal totalOrderValue = cartItems.stream()
                .map(CartItem::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderRequestDTO.builder()
                .userId(userId)
                .orderDate(LocalDateTime.now())
                .orderStatus("Pending")
                .promoDiscount(BigDecimal.valueOf(50.00))
                .orderTotal(totalOrderValue.subtract(BigDecimal.valueOf(50.00)))
                .paymentMode("Credit Card")
                .orderItemRequestDTOS(orderItemRequestDTOS)
                .build();
    }

}
