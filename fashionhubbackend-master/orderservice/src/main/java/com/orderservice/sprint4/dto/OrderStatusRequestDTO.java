package com.orderservice.sprint4.dto;

import com.orderservice.sprint4.model.enmus.OrderStatus;

public class OrderStatusRequestDTO {
    private Integer orderId;
    private OrderStatus orderStatus;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
