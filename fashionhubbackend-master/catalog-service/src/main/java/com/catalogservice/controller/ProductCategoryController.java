package com.catalogservice.controller;

import com.catalogservice.dto.ProductCategoryDiscountResponseDto;
import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {
    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);
    private final ProductCategoryService service;

    @Autowired
    public ProductCategoryController(ProductCategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductCategoryResponseDto> save(@RequestBody ProductCategoryRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryResponseDto> update(@PathVariable Long id, @RequestBody ProductCategoryRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<ProductCategoryResponseDto> updateDiscount(@PathVariable Long categoryId, @RequestBody Double discount) {
        return ResponseEntity.ok(service.updateDiscountByCategoryId(categoryId, discount));
    }

    @GetMapping(path = "/productcategory")
    public ResponseEntity<ProductCategoryDiscountResponseDto> getDiscountBySku(@RequestParam String sku) {
        logger.info("Received GET request for /productcategory with sku: {}", sku);
        ProductCategoryDiscountResponseDto response = service.getDiscountBySku(sku);
        if (response == null) {
            logger.warn("No discount found for sku: {}", sku);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logger.info("Returning discount for sku: {}: {}", sku, response.getDiscount());
        return ResponseEntity.ok(response);
    }
}
