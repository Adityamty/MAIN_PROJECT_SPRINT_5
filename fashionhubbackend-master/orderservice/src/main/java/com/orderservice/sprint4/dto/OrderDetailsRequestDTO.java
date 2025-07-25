package com.orderservice.sprint4.dto;


import com.orderservice.sprint4.model.enmus.OrderStatus;
import com.orderservice.sprint4.model.enmus.PaymentMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailsRequestDTO {
    private Integer userId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private BigDecimal promoDiscount;
    private BigDecimal orderTotal;
    private List<OrderItemRequestDTO> orderItemRequestDTOS;
    private PaymentMode paymentMode;
    private Integer addressId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(BigDecimal promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<OrderItemRequestDTO> getOrderItemRequestDTOS() {
        return orderItemRequestDTOS;
    }

    public void setOrderItemRequestDTOS(List<OrderItemRequestDTO> orderItemRequestDTOS) {
        this.orderItemRequestDTOS = orderItemRequestDTOS;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getAddressId() {
        return addressId;
    }
}
