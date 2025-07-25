package com.orderservice.sprint4.controller;

import com.orderservice.sprint4.dto.*;
import com.orderservice.sprint4.model.OrderItem;
import com.orderservice.sprint4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<List<OrderItemInventoryDTO>> createOrder(@RequestBody OrderDetailsRequestDTO dto){
        try{
            List<OrderItemInventoryDTO> itmes = orderService.createOrderTransaction(dto);
            return ResponseEntity.ok(itmes);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.emptyList());
        }
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponseDTO> getOrderDetails(@PathVariable Integer orderId) {
        try {
            OrderDetailsResponseDTO orderDetails = orderService.getOrderDetails(orderId);
            return ResponseEntity.ok(orderDetails);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/list/{month}")
    public ResponseEntity<?> getOrdersList(@PathVariable Integer month){
        try{
            List<OrderSummaryDTO> orders = orderService.getOrders(month);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PatchMapping("/order/confirm")
    public void orderConfirmation(@RequestBody OrderStatusRequestDTO dto) {
        orderService.orderConfirm(dto);
    }





}
