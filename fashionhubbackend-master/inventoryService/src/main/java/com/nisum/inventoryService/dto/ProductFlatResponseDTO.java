package com.nisum.inventoryService.dto;

public class ProductFlatResponseDTO {
    private String status;
    private String sku;
    private int categoryId;

    public ProductFlatResponseDTO(String status, String sku, int categoryId) {
        this.status = status;
        this.sku = sku;
        this.categoryId = categoryId;
    }

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
