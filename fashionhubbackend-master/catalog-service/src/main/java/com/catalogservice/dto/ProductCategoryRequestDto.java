package com.catalogservice.dto;

import lombok.Data;

@Data
public class ProductCategoryRequestDto {
    private String sku;          // SKU of the product
    private Long categoryId;     // Foreign key to category
    private Integer price;       // Product price
    private Double discount;     // Discount percentage

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
}
