package com.nisum.inventoryService.repository;
import com.nisum.inventoryService.dao.OrderInventory;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dao.OrderInventory;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findBySkuAndCategoryId(String sku, Integer categoryId);
    List<Inventory> findByAvailableQtyGreaterThan(int qty);
    @Query("SELECT DISTINCT i.sku FROM Inventory i WHERE i.availableQty > 0")
    List<String> findDistinctSkuWhereAvailableQtyGreaterThanZero();
    Optional<Inventory> findBySku(String sku);

    // In OrderRepository.java

    @Query("SELECT o FROM OrderInventory o WHERE o.reservedQty > 0")
    List<Inventory> findActiveOrders();


}

