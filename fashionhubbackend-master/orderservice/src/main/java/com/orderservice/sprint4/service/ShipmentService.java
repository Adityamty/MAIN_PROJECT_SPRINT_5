package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dao.ShipmentItemDAO;
import com.orderservice.sprint4.dto.ShipmentDetailsResponseDTO;

import java.util.List;

public interface ShipmentService {
    ShipmentDetailsResponseDTO getShipmentItemsByOrderId(Integer orderId);
}
