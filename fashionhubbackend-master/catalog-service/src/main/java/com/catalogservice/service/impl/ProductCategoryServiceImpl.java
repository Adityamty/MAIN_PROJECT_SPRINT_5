package com.catalogservice.service.impl;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.dto.ProductCategoryDiscountResponseDto;
import com.catalogservice.mapper.ProductCategoryMapper;
import com.catalogservice.repository.ProductCategoryRepository;
import com.catalogservice.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository repo;
    private final ProductCategoryMapper mapper;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository repo, ProductCategoryMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ProductCategoryResponseDto save(ProductCategoryRequestDto dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public List<ProductCategoryResponseDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ProductCategoryResponseDto update(Long id, ProductCategoryRequestDto dto) {
        var entity = repo.findById(id).orElseThrow(
                () -> new RuntimeException("ProductCategory not found with id: " + id)
        );
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("ProductCategory not found with id: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public ProductCategoryResponseDto updateDiscountByCategoryId(Long categoryId, Double discount) {
        var entity = repo.findByCategoryId(categoryId)
            .orElseThrow(() -> new RuntimeException("ProductCategory not found with categoryId: " + categoryId));
        entity.setDiscount(discount);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public ProductCategoryDiscountResponseDto getDiscountBySku(String sku) {
        var entity = repo.findBySku(sku).orElseThrow(() -> new RuntimeException("ProductCategory not found for sku: " + sku));
        return new ProductCategoryDiscountResponseDto(entity.getSku(), entity.getDiscount());
    }
}
