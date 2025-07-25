package com.nisum.inventoryService.utils;

import com.nisum.inventoryService.dto.InventoryDTO;
import com.nisum.inventoryService.dao.Inventory;

public class InventoryMapper {

    public static InventoryDTO toDTO(Inventory entity) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(entity.getId());
        dto.setSku(entity.getSku());
        dto.setCategoryId(entity.getCategoryId());
        dto.setLocation(entity.getLocation());
        dto.setAvailableQty(entity.getAvailableQty());
        return dto;
    }

    public static Inventory toEntity(InventoryDTO dto) {
        Inventory entity = new Inventory();
        entity.setId(dto.getId());
        entity.setSku(dto.getSku());
        entity.setCategoryId(dto.getCategoryId());
        entity.setLocation(dto.getLocation());
        entity.setAvailableQty(dto.getAvailableQty());
        return entity;
    }
}
