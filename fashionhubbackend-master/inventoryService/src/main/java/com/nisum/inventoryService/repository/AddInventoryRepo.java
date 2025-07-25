package com.nisum.inventoryService.repository;

import com.nisum.inventoryService.dao.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AddInventoryRepo extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySku(String sku);     // ✅ Add this
    boolean existsBySku(String sku);               // ✅ Optional, but useful
}
