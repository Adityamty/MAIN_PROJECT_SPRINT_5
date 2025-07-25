package com.orderservice.sprint4.model;

import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "shipment_items")
public class ShipmentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "shipment_id")
//    private Shipment shipment;

    @OneToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "item_tracking_id")
    private String itemTrackingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status")
    private ShipmentItemStatus itemStatus;

    @Column(name = "shipment_date")
    private LocalDateTime shipmentDate;

    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate;

    public ShipmentItem(){}

    public ShipmentItem(Integer id, OrderItem orderItem, String itemTrackingId, ShipmentItemStatus itemStatus, LocalDateTime shipmentDate, LocalDateTime deliveredDate) {
        this.id = id;
        this.orderItem = orderItem;
        this.itemTrackingId = itemTrackingId;
        this.itemStatus = itemStatus;
        this.shipmentDate = shipmentDate;
        this.deliveredDate = deliveredDate;
    }

    public String getItemTrackingId() {
        return itemTrackingId;
    }

    public void setItemTrackingId(String itemTrackingId) {
        this.itemTrackingId = itemTrackingId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public ShipmentItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ShipmentItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }
}


//
//
//CREATE TABLE shipment_items (
//        id INT PRIMARY KEY IDENTITY,
//        order_item_id INT,
//        item_tracking_id VARCHAR(100),
//item_status VARCHAR(50),           -- e.g., "In Transit", "Delivered"
//shipment_date DATETIME,
//delivered_date DATETIME,
//FOREIGN KEY (order_item_id) REFERENCES order_items(id)
//        );
