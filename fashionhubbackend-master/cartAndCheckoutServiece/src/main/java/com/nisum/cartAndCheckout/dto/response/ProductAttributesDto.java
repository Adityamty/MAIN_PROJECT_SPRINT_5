package com.nisum.cartAndCheckout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributesDto {
    private String sku;
    private int productId;
    private String size;
    private BigDecimal price;
    private String productImage;
}

