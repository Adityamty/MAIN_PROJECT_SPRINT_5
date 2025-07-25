package com.nisum.inventoryService.service;

import com.nisum.inventoryService.dao.Inventory;

import java.util.List;

public interface AdjustInventoryService {
    Inventory adjustInventory(String sku, int quantity);
    Inventory cancelInventory(String sku, int quantity);
    List<Inventory> getall();
}
