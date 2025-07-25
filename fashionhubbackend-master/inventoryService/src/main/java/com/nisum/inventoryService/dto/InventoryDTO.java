package com.nisum.inventoryService.dto;

public class InventoryDTO {
    private long id;
    private String sku;
    private int categoryId;     // ✅ Added this field
    private String location;
    private int availableQty;

    public InventoryDTO(long id, String sku, int categoryId, String location, int availableQty) {
        this.id = id;
        this.sku = sku;
        this.categoryId = categoryId;
        this.location = location;
        this.availableQty = availableQty;
    }

    public InventoryDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getAvailableQty() { return availableQty; }
    public void setAvailableQty(int availableQty) { this.availableQty = availableQty; }
}
