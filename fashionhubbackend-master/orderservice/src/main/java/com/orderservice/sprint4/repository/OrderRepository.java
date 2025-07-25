package com.orderservice.sprint4.repository;

import com.orderservice.sprint4.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.orderDate >= :cutoffDate")
    List<Order> findRecentOrdersByUserId(
            @Param("userId") Integer userId,
            @Param("cutoffDate") LocalDateTime cutoffDate
    );
}
