package com.nisum.cartAndCheckout.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Builder
public class CartItemRequestDTO {
    private Integer userId;
    private Integer productId;
    private String sku;
    private String size;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal finalPrice;
}
