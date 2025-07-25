package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryResponseDtoTest {
    @Test
    void testSettersAndGetters() {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
        dto.setProductCategoryId(100L);
        dto.setSku("sku-123");
        dto.setCategoryId(200L);
        dto.setPrice(500);
        dto.setDiscount(10.5);
        dto.setDiscountedPrice(447.5);

        assertEquals(100L, dto.getProductCategoryId());
        assertEquals("sku-123", dto.getSku());
        assertEquals(200L, dto.getCategoryId());
        assertEquals(500, dto.getPrice());
        assertEquals(10.5, dto.getDiscount());
        assertEquals(447.5, dto.getDiscountedPrice());
    }

    @Test
    void testDefaultValues() {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
        assertNull(dto.getProductCategoryId());
        assertNull(dto.getSku());
        assertNull(dto.getCategoryId());
        assertNull(dto.getPrice());
        assertNull(dto.getDiscount());
        assertNull(dto.getDiscountedPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductCategoryResponseDto dto1 = new ProductCategoryResponseDto();
        ProductCategoryResponseDto dto2 = new ProductCategoryResponseDto();
        dto1.setProductCategoryId(1L);
        dto1.setSku("sku-1");
        dto1.setCategoryId(2L);
        dto1.setPrice(100);
        dto1.setDiscount(5.0);
        dto1.setDiscountedPrice(95.0);
        dto2.setProductCategoryId(1L);
        dto2.setSku("sku-1");
        dto2.setCategoryId(2L);
        dto2.setPrice(100);
        dto2.setDiscount(5.0);
        dto2.setDiscountedPrice(95.0);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
        dto.setProductCategoryId(1L);
        dto.setSku("sku-1");
        dto.setCategoryId(2L);
        dto.setPrice(100);
        dto.setDiscount(5.0);
        dto.setDiscountedPrice(95.0);
        String str = dto.toString();
        assertTrue(str.contains("sku-1"));
        assertTrue(str.contains("ProductCategoryResponseDto"));
    }
}
