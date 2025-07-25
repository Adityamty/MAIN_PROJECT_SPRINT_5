package com.nisum.inventoryService.dto;

public class ReserveRequest {
    private Integer orderId;
    private String sku;
    private Integer categoryId;
    private Integer reservedQty;

    public ReserveRequest(String sku, Integer reservedQty, Integer categoryId, Integer orderId) {
        this.sku = sku;
        this.reservedQty = reservedQty;
        this.categoryId = categoryId;
        this.orderId = orderId;
    }

    // Getters
    public Integer getOrderId() {
        System.out.println(orderId);
        return orderId;
    }

    public String getSku() {
        System.out.println(sku);
        return sku;
    }

    public Integer getCategoryId() {
        System.out.println(categoryId);
        return categoryId;
    }

    public Integer getReservedQty() {
        System.out.println(reservedQty);
        return reservedQty;
    }

    // Setters
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setReservedQty(Integer reservedQty) {
        this.reservedQty = reservedQty;
    }
}
