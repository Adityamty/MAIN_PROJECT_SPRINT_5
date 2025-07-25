package com.nisum.inventoryService.utils;

import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dto.InventoryDTO;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    public Inventory mapToInventory(InventoryDTO dto) {
        Inventory inv = new Inventory();
        inv.setSku(dto.getSku());
        inv.setCategoryId(dto.getCategoryId()); // ✅ Important
        inv.setLocation(dto.getLocation());
        inv.setAvailableQty(dto.getAvailableQty());
        return inv;
    }

    public InventoryDTO mapToInventoryDto(Inventory inv) {
        InventoryDTO dto = new InventoryDTO();
        dto.setSku(inv.getSku());
        dto.setCategoryId(inv.getCategoryId()); // ✅ Important
        dto.setLocation(inv.getLocation());
        dto.setAvailableQty(inv.getAvailableQty());
        return dto;
    }
}
