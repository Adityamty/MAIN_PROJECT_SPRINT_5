package com.orderservice.sprint4.repository.custom;

import com.orderservice.sprint4.dao.ShipmentItemDAO;

import java.util.List;

public interface CustomShipmentItemRepository {
    List<ShipmentItemDAO> getShipmentItemsByOrderId(Integer orderId);
}
