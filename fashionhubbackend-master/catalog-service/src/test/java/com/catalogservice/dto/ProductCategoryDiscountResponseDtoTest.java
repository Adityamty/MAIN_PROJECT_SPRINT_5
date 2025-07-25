package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDiscountResponseDtoTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        String sku = "sku1";
        Double discount = 10.5;
        ProductCategoryDiscountResponseDto dto = new ProductCategoryDiscountResponseDto(sku, discount);
        assertEquals(sku, dto.getSku());
        assertEquals(discount, dto.getDiscount());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductCategoryDiscountResponseDto dto1 = new ProductCategoryDiscountResponseDto("sku1", 10.0);
        ProductCategoryDiscountResponseDto dto2 = new ProductCategoryDiscountResponseDto("sku1", 10.0);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductCategoryDiscountResponseDto dto = new ProductCategoryDiscountResponseDto("sku1", 10.0);
        assertTrue(dto.toString().contains("sku1"));
    }
}
