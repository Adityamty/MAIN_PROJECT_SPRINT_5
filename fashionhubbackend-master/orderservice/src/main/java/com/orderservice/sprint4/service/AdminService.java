package com.orderservice.sprint4.service;


import com.orderservice.sprint4.dto.OrderItemStatusRequestDTO;
import com.orderservice.sprint4.dto.OrderStatusRequestDTO;
import com.orderservice.sprint4.dto.ShipmentItemListDTO;
import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;

import java.util.List;

public interface AdminService {

    void orderShipped(OrderItemStatusRequestDTO dto);

    void orderDelivered(OrderItemStatusRequestDTO dto);

    List<ShipmentItemListDTO> getShipmentByStatus(ShipmentItemStatus itemStatus);

    void orderCanelled(OrderItemStatusRequestDTO dto);
}
