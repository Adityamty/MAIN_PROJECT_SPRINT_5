package com.nisum.inventoryService.service;

import com.nisum.inventoryService.dto.OrderInventoryDTO;
import java.util.List;

public interface OrderInventoryService {
    OrderInventoryDTO createOrder(OrderInventoryDTO dto);
    OrderInventoryDTO getOrderById(Integer id);
    List<OrderInventoryDTO> getAllOrders();
    OrderInventoryDTO updateOrder(Integer id, OrderInventoryDTO dto);
    void deleteOrder(Integer id);
    List<OrderInventoryDTO> getActiveOrders();
    void allocateInventory(Integer orderId);
    void cancelOrder(Integer orderId);


//    void reserveInventory(ReserveInventoryRequestDTO request);

}
