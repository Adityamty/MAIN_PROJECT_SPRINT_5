package com.orderservice.sprint4.dto;

import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;

public class OrderItemStatusRequestDTO {
    private Integer orderItemId;
    private ShipmentItemStatus itemStatus;

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ShipmentItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ShipmentItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }
}
