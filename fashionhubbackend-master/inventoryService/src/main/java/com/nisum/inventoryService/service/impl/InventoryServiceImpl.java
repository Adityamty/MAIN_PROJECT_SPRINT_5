package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dto.InventoryDTO;
import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.repository.InventoryRepository;
import com.nisum.inventoryService.service.InventoryService;
import com.nisum.inventoryService.utils.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nisum.inventoryService.repository.OrderInventoryRepository;
import com.nisum.inventoryService.dto.ReserveRequest;
import com.nisum.inventoryService.dto.ActiveInventoryDTO;
import org.springframework.transaction.annotation.Transactional;
import com.nisum.inventoryService.exception.ResourceNotFoundException;
import com.nisum.inventoryService.dao.OrderInventory;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;



    @Override
    public Inventory addInventory(Inventory inventory) {
        return repository.save(inventory);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    @Override
    public Inventory updateInventory(Integer id, Inventory updated) {
        Inventory existing = repository.findById(id).orElseThrow();
        existing.setSku(updated.getSku());
        existing.setCategoryId(updated.getCategoryId());
        existing.setLocation(updated.getLocation());
        existing.setAvailableQty(updated.getAvailableQty());
        return repository.save(existing);
    }

    @Override
    public void deleteInventory(Integer id) {
        repository.deleteById(id);
    }


    @Autowired
    private OrderInventoryRepository orderInventoryRepository;

    @Override
    @Transactional
    public void reserveInventory(ReserveRequest request) {
        System.out.println("Reserve Request: " + request);
        // ❗ Step 0: Check if orderId already exists
        boolean exists = orderInventoryRepository.existsByOrderId(request.getOrderId());
        if (exists) {
            throw new IllegalArgumentException("Order ID " + request.getOrderId() + " already exists");
        }

        // Step 1: Fetch inventory by SKU and Category ID
        Inventory inventory = repository.findBySkuAndCategoryId(request.getSku(), request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for SKU: "
                        + request.getSku() + " and Category ID: " + request.getCategoryId()));

        // Step 2: Check available quantity
        if (inventory.getAvailableQty() < request.getReservedQty()) {
            throw new IllegalArgumentException("Not enough inventory available");
        }

        // Step 3: Decrease inventory available quantity
        inventory.setAvailableQty(inventory.getAvailableQty() - request.getReservedQty());
        repository.save(inventory);

        // Step 4: Add record to order_inventory
        OrderInventory orderInventory = new OrderInventory();
        orderInventory.setOrderId(request.getOrderId());
        orderInventory.setSku(request.getSku());
        orderInventory.setCategoryId(request.getCategoryId());
        orderInventory.setReservedQty(request.getReservedQty());
        orderInventory.setAllocatedQty(0);
        orderInventory.setIsCancelled(false);

        orderInventoryRepository.save(orderInventory);
    }

    @Override
    public List<ActiveInventoryDTO> getActiveInventory() {
        return repository.findByAvailableQtyGreaterThan(0)
                .stream()
                .map(inventory -> new ActiveInventoryDTO(
                        inventory.getSku(),
                        inventory.getCategoryId(),
                        inventory.getAvailableQty()))
                .collect(Collectors.toList());
    }

    @Override
    public String getAvailableQuantity(String sku) {
        return repository.findBySku(sku)
                .map(inventory -> {
                    int qty = inventory.getAvailableQty();
                    if (qty == 0) {
                        return "out of stock";
                    } else {
                        return String.valueOf(qty);
                    }
                })
                .orElse("sku does not exist");
    }




}
