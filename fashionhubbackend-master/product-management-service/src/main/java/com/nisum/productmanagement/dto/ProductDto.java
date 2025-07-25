package com.nisum.productmanagement.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDto {
    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String status;
    private LocalDateTime lastModifiedDate;
    private Long sellerId;
    private List<ProductAttributeDto> attributes;

    public ProductDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; }

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public List<ProductAttributeDto> getAttributes() { return attributes; }
    public void setAttributes(List<ProductAttributeDto> attributes) { this.attributes = attributes; }
}
