package com.catalogservice.mapper;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.model.ProductCategory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryMapperTest {
    private final ProductCategoryMapper mapper = new ProductCategoryMapper();

    @Test
    void testToEntity() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        dto.setSku("sku-1");
        dto.setCategoryId(2L);
        dto.setPrice(100);
        dto.setDiscount(5.0);
        ProductCategory entity = mapper.toEntity(dto);
        assertEquals("sku-1", entity.getSku());
        assertEquals(2L, entity.getCategoryId());
        assertEquals(100, entity.getPrice());
        assertEquals(5.0, entity.getDiscount());
    }

    @Test
    void testToDtoWithDiscountedPrice() {
        ProductCategory entity = new ProductCategory();
        entity.setProductCategoryId(1L);
        entity.setSku("sku-1");
        entity.setCategoryId(2L);
        entity.setPrice(100);
        entity.setDiscount(5.0);
        ProductCategoryResponseDto dto = mapper.toDto(entity);
        assertEquals(1L, dto.getProductCategoryId());
        assertEquals("sku-1", dto.getSku());
        assertEquals(2L, dto.getCategoryId());
        assertEquals(100, dto.getPrice());
        assertEquals(5.0, dto.getDiscount());
        assertEquals(95.0, dto.getDiscountedPrice());
    }

    @Test
    void testToDtoWithNulls() {
        ProductCategory entity = new ProductCategory();
        ProductCategoryResponseDto dto = mapper.toDto(entity);
        assertNull(dto.getDiscountedPrice());
    }

    @Test
    void testUpdateEntity() {
        ProductCategory entity = new ProductCategory();
        entity.setSku("old");
        entity.setCategoryId(1L);
        entity.setPrice(50);
        entity.setDiscount(2.0);
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        dto.setSku("new");
        dto.setCategoryId(2L);
        dto.setPrice(100);
        dto.setDiscount(5.0);
        mapper.updateEntity(entity, dto);
        assertEquals("new", entity.getSku());
        assertEquals(2L, entity.getCategoryId());
        assertEquals(100, entity.getPrice());
        assertEquals(5.0, entity.getDiscount());
    }
}
