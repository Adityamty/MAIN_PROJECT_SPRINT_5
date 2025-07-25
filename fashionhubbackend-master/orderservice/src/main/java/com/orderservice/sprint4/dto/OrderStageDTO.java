package com.orderservice.sprint4.dto;

public class OrderStageDTO {
    private Integer orderId;
    private Integer isCancelled;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }
}
