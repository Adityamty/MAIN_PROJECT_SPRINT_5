package com.orderservice.sprint4.model;

import com.orderservice.sprint4.model.enmus.OrderReturnStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_returns")
public class OrderReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer orderReturnId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "return_reason")
    private String returnReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "return_status")
    private OrderReturnStatus returnStatus;

    @Column(name = "refund_amount")
    private BigDecimal refundAmount;

    public Integer getOrderReturnId() {
        return orderReturnId;
    }

    public void setOrderReturnId(Integer orderReturnId) {
        this.orderReturnId = orderReturnId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public OrderReturnStatus getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(OrderReturnStatus returnStatus) {
        this.returnStatus = returnStatus;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
}


//CREATE TABLE order_returns (
//    id INT PRIMARY KEY IDENTITY,
//    order_item_id INT,
//    return_date DATETIME,
//    return_reason VARCHAR(255),
//    return_status VARCHAR(50), -- e.g., Requested, Approved, Rejected, Completed
//    refund_amount DECIMAL(10,2),
//    FOREIGN KEY (order_item_id) REFERENCES order_items(id)
//);