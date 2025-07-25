package com.nisum.inventoryService.repository;
import java.util.List;
import java.util.Optional;
import com.nisum.inventoryService.dao.OrderInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInventoryRepository extends JpaRepository<OrderInventory, Integer> {

    // ✅ Add this method to check if an orderId already exists
    boolean existsByOrderId(Integer orderId);

    List<OrderInventory> findByReservedQtyGreaterThan(int qty);

    Optional<OrderInventory> findByOrderId(Integer orderId);
}
