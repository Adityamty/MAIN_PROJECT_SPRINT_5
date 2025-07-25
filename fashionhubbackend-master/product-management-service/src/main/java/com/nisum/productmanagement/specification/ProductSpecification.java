package com.nisum.productmanagement.specification;

import com.nisum.productmanagement.model.Product;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {
    public static Specification<Product> searchProducts(String search) {
        return (root, query, cb) -> {
            String likePattern = "%" + search.toLowerCase() + "%";
            Predicate namePredicate = cb.like(cb.lower(root.get("name")), likePattern);
            return cb.or(namePredicate);
        };
    }

    public static Specification<Product> hasStatus(String status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, cb) -> cb.equal(root.get("categoryId"), categoryId);
    }

    public static Specification<Product> priceBetween(Double minPrice, Double maxPrice) {
        return (root, query, cb) -> {
            var attributeJoin = root.join("attributes");
            Predicate pricePredicate = null;
            
            if (minPrice != null && maxPrice != null) {
                pricePredicate = cb.between(attributeJoin.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                pricePredicate = cb.greaterThanOrEqualTo(attributeJoin.get("price"), minPrice);
            } else if (maxPrice != null) {
                pricePredicate = cb.lessThanOrEqualTo(attributeJoin.get("price"), maxPrice);
            }
            
            return pricePredicate;
        };
    }

    public static Specification<Product> priceGreaterThanOrEqual(Double minPrice) {
        return (root, query, cb) -> {
            var attributeJoin = root.join("attributes");
            return cb.greaterThanOrEqualTo(attributeJoin.get("price"), minPrice);
        };
    }

    public static Specification<Product> searchProductsForAttributes(String search) {
        return (root, query, cb) -> {
            String likePattern = "%" + search.toLowerCase() + "%";

            // Search in product name
            Predicate namePredicate = cb.like(cb.lower(root.get("name")), likePattern);

            // Search in attribute SKUs
            Predicate skuPredicate = cb.like(cb.lower(root.join("attributes").get("sku")), likePattern);

            // Search in attribute sizes
            Predicate sizePredicate = cb.like(cb.lower(root.join("attributes").get("size")), likePattern);

            return cb.or(namePredicate, skuPredicate, sizePredicate);
        };
    }
}
