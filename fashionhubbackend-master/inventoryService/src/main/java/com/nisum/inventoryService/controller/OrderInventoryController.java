package com.nisum.inventoryService.controller;

import com.nisum.inventoryService.dto.OrderInventoryDTO;
import com.nisum.inventoryService.dto.ReserveRequest;
import com.nisum.inventoryService.service.InventoryService;
import com.nisum.inventoryService.service.OrderInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/orders")
public class OrderInventoryController {

    @Autowired
    private OrderInventoryService service;

    @Autowired
    private InventoryService inventoryService; // ✅ FIXED: Inject InventoryService

    @PostMapping
    public ResponseEntity<OrderInventoryDTO> createOrder(@RequestBody OrderInventoryDTO dto) {
        return ResponseEntity.ok(service.createOrder(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderInventoryDTO> getOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderInventoryDTO>> getAllOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderInventoryDTO> updateOrder(@PathVariable Integer id, @RequestBody OrderInventoryDTO dto) {
        return ResponseEntity.ok(service.updateOrder(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody ReserveRequest request) {
        System.out.println(1);
        request.getOrderId();
        System.out.println(2);
        request.getSku();
        System.out.println(3);
        request.getCategoryId();
        System.out.println(4);
        request.getReservedQty();
        inventoryService.reserveInventory(request);
        return ResponseEntity.ok("Inventory reserved successfully");
    }

    @GetMapping("/active")
    public ResponseEntity<List<OrderInventoryDTO>> getActiveOrders() {
        return ResponseEntity.ok(service.getActiveOrders());
    }

    @PutMapping("/allocate/{orderId}")
    public ResponseEntity<String> allocateOrder(@PathVariable Integer orderId) {
        service.allocateInventory(orderId);
        return ResponseEntity.ok("Order allocated successfully.");
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled and inventory updated.");
    }


}
