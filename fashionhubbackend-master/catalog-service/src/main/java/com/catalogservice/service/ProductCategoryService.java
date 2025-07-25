package com.catalogservice.service;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.dto.ProductCategoryDiscountResponseDto;

import java.util.List;

public interface ProductCategoryService {
    ProductCategoryResponseDto save(ProductCategoryRequestDto dto);
    List<ProductCategoryResponseDto> getAll();
    ProductCategoryResponseDto update(Long id, ProductCategoryRequestDto dto);
    void delete(Long id);
    ProductCategoryResponseDto updateDiscountByCategoryId(Long categoryId, Double discount);
    ProductCategoryDiscountResponseDto getDiscountBySku(String sku);
}
