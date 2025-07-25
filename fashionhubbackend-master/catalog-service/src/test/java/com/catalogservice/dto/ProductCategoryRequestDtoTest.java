package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryRequestDtoTest {
    @Test
    void testSettersAndGetters() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        dto.setSku("sku-123");
        dto.setCategoryId(10L);
        dto.setPrice(500);
        dto.setDiscount(5.0);
        assertEquals("sku-123", dto.getSku());
        assertEquals(10L, dto.getCategoryId());
        assertEquals(500, dto.getPrice());
        assertEquals(5.0, dto.getDiscount());
    }

    @Test
    void testAllFields() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        dto.setSku("sku-123");
        dto.setCategoryId(10L);
        dto.setPrice(500);
        dto.setDiscount(15.5);
        assertEquals("sku-123", dto.getSku());
        assertEquals(10L, dto.getCategoryId());
        assertEquals(500, dto.getPrice());
        assertEquals(15.5, dto.getDiscount());
    }

    @Test
    void testDefaultValues() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        assertNull(dto.getSku());
        assertNull(dto.getCategoryId());
        assertNull(dto.getPrice());
        assertNull(dto.getDiscount());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductCategoryRequestDto dto1 = new ProductCategoryRequestDto();
        ProductCategoryRequestDto dto2 = new ProductCategoryRequestDto();
        dto1.setSku("sku-1");
        dto1.setCategoryId(2L);
        dto1.setPrice(100);
        dto1.setDiscount(5.0);
        dto2.setSku("sku-1");
        dto2.setCategoryId(2L);
        dto2.setPrice(100);
        dto2.setDiscount(5.0);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        dto.setSku("sku-1");
        dto.setCategoryId(2L);
        dto.setPrice(100);
        dto.setDiscount(5.0);
        String str = dto.toString();
        assertTrue(str.contains("sku-1"));
        assertTrue(str.contains("ProductCategoryRequestDto"));
    }
}
