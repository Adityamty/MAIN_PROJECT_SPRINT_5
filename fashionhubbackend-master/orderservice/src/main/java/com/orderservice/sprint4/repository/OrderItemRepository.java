package com.orderservice.sprint4.repository;

import com.orderservice.sprint4.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
