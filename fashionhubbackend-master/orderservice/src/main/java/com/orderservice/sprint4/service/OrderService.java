package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dto.*;
import com.orderservice.sprint4.model.OrderItem;

import java.util.List;

public interface OrderService {

    void orderConfirm(OrderStatusRequestDTO dto);
    List<OrderItemInventoryDTO> createOrderTransaction(OrderDetailsRequestDTO dto);
    OrderDetailsResponseDTO getOrderDetails(Integer orderId);
    List<OrderSummaryDTO> getOrders(Integer months);
}
