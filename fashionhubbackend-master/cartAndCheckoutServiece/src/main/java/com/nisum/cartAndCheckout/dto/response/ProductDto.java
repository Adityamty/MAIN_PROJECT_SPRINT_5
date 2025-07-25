package com.nisum.cartAndCheckout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private int productId;
    private String sku;
    private String name;
    private int categoryId;
    private String status;
    private LocalDateTime lastModifiedDate;
    private int sellerId;
}
