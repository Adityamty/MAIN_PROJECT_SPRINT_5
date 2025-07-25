package com.nisum.inventoryService.dto;

public class OrderInventoryDTO {
    private Integer id;
    private Integer orderId;
    private String sku;
    private Integer categoryId;
    private Integer reservedQty;
    private Integer allocatedQty;
    private Boolean isCancelled;

    public OrderInventoryDTO() {}

    public OrderInventoryDTO(Integer id, Integer orderId, String sku, Integer categoryId, Integer reservedQty, Integer allocatedQty, Boolean isCancelled) {
        this.id = id;
        this.orderId = orderId;
        this.sku = sku;
        this.categoryId = categoryId;
        this.reservedQty = reservedQty;
        this.allocatedQty = allocatedQty;
        this.isCancelled = isCancelled;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getOrderId() { return orderId; }

    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public String getSku() { return sku; }

    public void setSku(String sku) { this.sku = sku; }

    public Integer getCategoryId() { return categoryId; }

    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Integer getReservedQty() { return reservedQty; }

    public void setReservedQty(Integer reservedQty) { this.reservedQty = reservedQty; }

    public Integer getAllocatedQty() { return allocatedQty; }

    public void setAllocatedQty(Integer allocatedQty) { this.allocatedQty = allocatedQty; }

    public Boolean getIsCancelled() { return isCancelled; }

    public void setIsCancelled(Boolean isCancelled) { this.isCancelled = isCancelled; }
}
