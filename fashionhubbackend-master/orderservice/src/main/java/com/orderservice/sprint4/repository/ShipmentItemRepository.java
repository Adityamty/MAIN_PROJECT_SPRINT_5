package com.orderservice.sprint4.repository;

import com.orderservice.sprint4.dao.ShipmentItemDAO;
import com.orderservice.sprint4.model.ShipmentItem;
import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipmentItemRepository extends JpaRepository<ShipmentItem,Integer> {

    @Query(value = """
        SELECT 
            oi.product_id AS product,
            si.item_tracking_id AS trackingId,
            oi.sku AS sku,
            oi.quantity AS quantity,
            oi.seller_id AS seller,
            si.item_status AS itemStatus,
            si.shipment_date AS shipmentDate,
            si.delivered_date AS deliveredDate
        FROM shipment_items si
        JOIN order_items oi ON si.order_item_id = oi.id
        WHERE oi.order_id = :orderId
    """, nativeQuery = true)
    List<ShipmentItemDAO> findShipmentItemsByOrderId(@Param("orderId") Integer orderId);


    @Query("SELECT si FROM ShipmentItem si WHERE si.orderItem.orderItemId = :orderItemId")
    ShipmentItem findByOrderItemOrderItemId(@Param("orderItemId") Integer orderItemId);

    List<ShipmentItem> findByItemStatus(ShipmentItemStatus itemStatus);
}
