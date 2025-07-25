package com.orderservice.sprint4.dto;

import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


public class ShipmentItemDTO {
    private String product;
    private String trackingId;
    private String sku;
    private Integer quantity;
    private String seller;
    private ShipmentItemStatus itemStatus;
    private LocalDateTime shipmentDate;
    private LocalDateTime deliveredDate;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public ShipmentItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ShipmentItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
}
