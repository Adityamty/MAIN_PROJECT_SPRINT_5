package com.nisum.inventoryService.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDto {
    private int id;
    private String name;
    private int categoryId;
    private String categoryName;
    private String status;
    private LocalDateTime lastModifiedDate;
    private int sellerId;
    private List<ProductAttributeDto> attributes;

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getSellerId() {
        return sellerId;
    }
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public List<ProductAttributeDto> getAttributes() {
        return attributes;
    }
    public void setAttributes(List<ProductAttributeDto> attributes) {
        this.attributes = attributes;
    }
}

