package com.catalogservice.mapper;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.model.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {

    public ProductCategory toEntity(ProductCategoryRequestDto dto) {
        ProductCategory entity = new ProductCategory();
        entity.setSku(dto.getSku());
        entity.setCategoryId(dto.getCategoryId());
        entity.setPrice(dto.getPrice());
        entity.setDiscount(dto.getDiscount());
        return entity;
    }

    public ProductCategoryResponseDto toDto(ProductCategory entity) {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
        dto.setProductCategoryId(entity.getProductCategoryId());
        dto.setSku(entity.getSku());
        dto.setCategoryId(entity.getCategoryId());
        dto.setPrice(entity.getPrice());
        dto.setDiscount(entity.getDiscount());
        if (entity.getPrice() != null && entity.getDiscount() != null) {
            dto.setDiscountedPrice(
                entity.getPrice() - (entity.getPrice() * entity.getDiscount() / 100)
            );
        } else {
            dto.setDiscountedPrice(null);
        }
        return dto;
    }

    public void updateEntity(ProductCategory entity, ProductCategoryRequestDto dto) {
        if (dto.getSku() != null) {
            entity.setSku(dto.getSku());
        }
        if (dto.getCategoryId() != null) {
            entity.setCategoryId(dto.getCategoryId());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
        if (dto.getDiscount() != null) {
            entity.setDiscount(dto.getDiscount());
        }
    }
}
