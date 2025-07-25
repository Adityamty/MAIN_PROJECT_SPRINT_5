package com.nisum.inventoryService.dto;

public class ActiveInventoryDTO {

    private String sku;
    private Integer categoryId;
    private Integer availableQty;

    public ActiveInventoryDTO() {
    }

    public ActiveInventoryDTO(String sku, Integer categoryId, Integer availableQty) {
        this.sku = sku;
        this.categoryId = categoryId;
        this.availableQty = availableQty;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }
}
