    package com.orderservice.sprint4.dto;

    import com.orderservice.sprint4.model.enmus.OrderStatus;
    import lombok.Builder;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    public class OrderSummaryDTO {
        private Integer orderId;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private BigDecimal orderTotal;
        private Long items;

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
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

        public BigDecimal getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(BigDecimal orderTotal) {
            this.orderTotal = orderTotal;
        }

        public Long getItems() {
            return items;
        }

        public void setItems(Long items) {
            this.items = items;
        }
    }
