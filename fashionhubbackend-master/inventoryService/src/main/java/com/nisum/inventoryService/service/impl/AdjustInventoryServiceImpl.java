package com.nisum.inventoryService.service;

import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdjustInventoryServiceImpl implements AdjustInventoryService {

    @Autowired
    private InventoryRepository repository;

    @Override
    public Inventory adjustInventory(String sku, int quantity) {
        Optional<Inventory> optional = repository.findBySku(sku.trim());
        if (optional.isEmpty()) return null;

        Inventory inventory = optional.get();
        inventory.setAvailableQty(inventory.getAvailableQty() + quantity);
        return repository.save(inventory);
    }

    @Override
    public Inventory cancelInventory(String sku, int quantity) {
        Optional<Inventory> optional = repository.findBySku(sku.trim());
        if (optional.isEmpty()) return null;

        Inventory inventory = optional.get();
        int newQty = Math.max(0, inventory.getAvailableQty() - quantity);
        inventory.setAvailableQty(newQty);
        return repository.save(inventory);
    }
    public List<Inventory > getall()
    {
        return repository.findAll();
    }
}
