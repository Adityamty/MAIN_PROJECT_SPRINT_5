package com.nisum.cartAndCheckout.mapper;

import com.nisum.cartAndCheckout.dto.request.BillItemDTO;
import com.nisum.cartAndCheckout.dto.response.BillResponseDTO;
import com.nisum.cartAndCheckout.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BillMapper {

    public static BillResponseDTO toBillResponseDTO(Integer userId, List<CartItem> cartItems) {
        List<BillItemDTO> items = cartItems.stream().map(item -> BillItemDTO.builder()
                .sku(item.getSku())
                .size(item.getSize())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .discount(item.getDiscount())
                .finalPrice(item.getFinalPrice())
                .build()).collect(Collectors.toList());

        BigDecimal totalAmount = cartItems.stream()
                .map(CartItem::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BillResponseDTO.builder()
                .userId(userId)
                .items(items)
                .totalAmount(totalAmount)
                .build();
    }
}
