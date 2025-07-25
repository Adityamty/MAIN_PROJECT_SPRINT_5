package com.nisum.inventoryService.service;

import com.nisum.inventoryService.dto.InventoryDTO;
import java.util.List;
import java.util.Map;
public interface AddInventoryService {

    InventoryDTO addInventory(InventoryDTO dto); // ✅ Must match
    List<InventoryDTO> getAllInventory();
    public List<Map<String, Object>> getAvailabilityForSkus(List<String> skuList);

}
