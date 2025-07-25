package com.catalogservice.repository;

import com.catalogservice.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findByCategoryId(Long categoryId);
    Optional<ProductCategory> findBySku(String sku);
}
