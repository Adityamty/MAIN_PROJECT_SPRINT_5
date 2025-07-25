package com.nisum.inventoryService.service;

import com.nisum.inventoryService.dto.ActiveInventoryDTO;
import com.nisum.inventoryService.dto.InventoryDTO;

import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dto.ReserveRequest;

import java.util.List;

public interface InventoryService {
    Inventory addInventory(Inventory inventory);
    List<Inventory> getAllInventory();
    Inventory updateInventory(Integer id, Inventory inventory);
    void deleteInventory(Integer id);
    void reserveInventory(ReserveRequest request);
    List<ActiveInventoryDTO> getActiveInventory();
    String getAvailableQuantity(String sku);


}
