package com.nisum.inventoryService.utils;

import com.nisum.inventoryService.dto.OrderInventoryDTO;
import com.nisum.inventoryService.dao.OrderInventory; // ✅ Import the entity


public class OrderInventoryMapper {

    public static OrderInventoryDTO toDTO(OrderInventory entity) {
        OrderInventoryDTO dto = new OrderInventoryDTO();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrderId());
        dto.setSku(entity.getSku());
        dto.setCategoryId(entity.getCategoryId());
        dto.setReservedQty(entity.getReservedQty());
        dto.setAllocatedQty(entity.getAllocatedQty());
        dto.setIsCancelled(entity.getIsCancelled());
        return dto;
    }

    public static OrderInventory toEntity(OrderInventoryDTO dto) {
        OrderInventory entity = new OrderInventory();
        entity.setId(dto.getId());
        entity.setOrderId(dto.getOrderId());
        entity.setSku(dto.getSku());
        entity.setCategoryId(dto.getCategoryId());
        entity.setReservedQty(dto.getReservedQty());
        entity.setAllocatedQty(dto.getAllocatedQty());
        entity.setIsCancelled(dto.getIsCancelled());
        return entity;
    }
}

