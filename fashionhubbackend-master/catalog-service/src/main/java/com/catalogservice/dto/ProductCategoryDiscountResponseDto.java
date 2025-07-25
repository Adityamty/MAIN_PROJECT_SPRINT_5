package com.catalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCategoryDiscountResponseDto {
    private String sku;
    private Double discount;

    public ProductCategoryDiscountResponseDto(String sku, Double discount) {
        this.sku = sku;
        this.discount = discount;
    }

    public Double getDiscount() {
        return discount;
    }

    public String getSku() {
        return sku;
    }
}
