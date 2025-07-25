package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dto.OrderInventoryDTO;
import com.nisum.inventoryService.dao.OrderInventory;
import com.nisum.inventoryService.exception.ResourceNotFoundException;
import com.nisum.inventoryService.repository.OrderInventoryRepository;
import com.nisum.inventoryService.service.OrderInventoryService;
import com.nisum.inventoryService.utils.OrderInventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nisum.inventoryService.repository.InventoryRepository;
import com.nisum.inventoryService.dao.Inventory;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInventoryServiceImpl implements OrderInventoryService {

    @Autowired
    private OrderInventoryRepository repository;

    @Override
    public OrderInventoryDTO createOrder(OrderInventoryDTO dto) {
        OrderInventory entity = OrderInventoryMapper.toEntity(dto);
        return OrderInventoryMapper.toDTO(repository.save(entity));
    }

    @Override
    public OrderInventoryDTO getOrderById(Integer id) {
        OrderInventory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return OrderInventoryMapper.toDTO(entity);
    }


    @Override
    public List<OrderInventoryDTO> getAllOrders() {
        return repository.findAll()
                .stream()
                .map(OrderInventoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderInventoryDTO updateOrder(Integer id, OrderInventoryDTO dto) {
        OrderInventory existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        existing.setOrderId(dto.getOrderId());
        existing.setSku(dto.getSku());
        existing.setCategoryId(dto.getCategoryId());
        existing.setReservedQty(dto.getReservedQty());
        existing.setAllocatedQty(dto.getAllocatedQty());
        existing.setIsCancelled(dto.getIsCancelled());

        return OrderInventoryMapper.toDTO(repository.save(existing));
    }

    @Override
    public void deleteOrder(Integer id) {
        OrderInventory existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        repository.delete(existing);
    }

    @Override
    public List<OrderInventoryDTO> getActiveOrders() {
        return repository.findByReservedQtyGreaterThan(0)
                .stream()
                .map(OrderInventoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void allocateInventory(Integer orderId) {
        OrderInventory entity = repository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with orderId: " + orderId));

        if (entity.getReservedQty() <= 0) {
            throw new IllegalStateException("No reserved quantity available to allocate.");
        }

        // Allocation logic
        int reserved = entity.getReservedQty();
        entity.setAllocatedQty(entity.getAllocatedQty() + reserved);
        entity.setReservedQty(0);

        repository.save(entity);
    }

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void cancelOrder(Integer orderId) {
        OrderInventory order = repository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with orderId: " + orderId));

        if (order.getIsCancelled()) {
            throw new IllegalStateException("Order is already cancelled.");
        }

        String sku = order.getSku();
        Integer categoryId = order.getCategoryId();
        Integer reservedQty = order.getReservedQty();

        if (reservedQty <= 0) {
            throw new IllegalStateException("No reserved quantity to cancel.");
        }

        // Find the corresponding inventory record
        Inventory inventory = inventoryRepository.findBySkuAndCategoryId(sku, categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory not found for SKU: " + sku + " and Category ID: " + categoryId));

        // Update inventory: return reserved qty to available qty
        inventory.setAvailableQty(inventory.getAvailableQty() + reservedQty);

        // Update order
        order.setReservedQty(0);
        order.setIsCancelled(true);

        // Save both
        inventoryRepository.save(inventory);
        repository.save(order);
    }





}
