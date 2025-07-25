package com.catalogservice.dto;

import lombok.Data;

@Data
public class ProductCategoryResponseDto {
    private Long productCategoryId;  // Primary key
    private String sku;
    private Long categoryId;
    private Integer price;
    private Double discount;
    private Double discountedPrice;  // price - (price * discount/100)

    public Long getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(Long productCategoryId) { this.productCategoryId = productCategoryId; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
    public Double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(Double discountedPrice) { this.discountedPrice = discountedPrice; }
}
