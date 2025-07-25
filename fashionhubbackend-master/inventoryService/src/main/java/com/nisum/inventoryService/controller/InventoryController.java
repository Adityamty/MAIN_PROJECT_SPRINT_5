package com.nisum.inventoryService.controller;

import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dto.*;
import com.nisum.inventoryService.repository.AddInventoryRepo;
import com.nisum.inventoryService.service.AddInventoryService;
import com.nisum.inventoryService.service.AdjustInventoryService;
import com.nisum.inventoryService.service.InventoryService;

import com.nisum.inventoryService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private AdjustInventoryService adjustInventoryService;

    @Autowired
    private AddInventoryService addInventoryService;

    @Autowired
    private AddInventoryRepo addInventoryRepo;

    @Autowired
    private ProductService productService;
    // -------------------- CRUD & General Inventory APIs --------------------

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.addInventory(inventory));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addInventoryDto(@RequestBody InventoryDTO inventoryDto) {
        try {
            addInventoryService.addInventory(inventoryDto);
            return ResponseEntity.ok("{\"message\": \"Inventory added successfully\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"Database insert failed\"}");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Integer id, @RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Integer id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------- Reserve & Quantity APIs --------------------

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody ReserveRequest request) {
        inventoryService.reserveInventory(request);
        return ResponseEntity.ok("Inventory reserved successfully");
    }

    @GetMapping("/available/{sku}")
    public ResponseEntity<String> getAvailableQuantity(@PathVariable String sku) {
        // Prefer logic from InventoryService, fallback to repo if needed
        return addInventoryRepo.findBySku(sku)
                .map(inventory -> {
                    int qty = inventory.getAvailableQty();
                    if (qty == 0) return ResponseEntity.ok("out of stock");
                    else return ResponseEntity.ok(String.valueOf(qty));
                })
                .orElseGet(() -> ResponseEntity.ok("sku does not exist"));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ActiveInventoryDTO>> getActiveInventory() {
        return ResponseEntity.ok(inventoryService.getActiveInventory());
    }

    // -------------------- Adjust/Cancel APIs --------------------

    @PutMapping("/adjust")
    public ResponseEntity<?> adjustInventory(@RequestBody AdjustRequest request) {
        Inventory updated = adjustInventoryService.adjustInventory(request.getSku(), request.getQuantity());
        if (updated != null) {
            return ResponseEntity.ok("Adjusted quantity. New availableQty: " + updated.getAvailableQty());
        } else {
            return ResponseEntity.status(404).body("SKU not found");
        }
    }

    @PutMapping("/adjust/cancel")
    public ResponseEntity<?> cancelInventory(@RequestBody AdjustRequest request) {
        Inventory updated = adjustInventoryService.cancelInventory(request.getSku(), request.getQuantity());
        if (updated != null) {
            return ResponseEntity.ok("Cancelled quantity. New availableQty: " + updated.getAvailableQty());
        } else {
            return ResponseEntity.status(404).body("SKU not found");
        }
    }

    @GetMapping("/adjust/all")
    public ResponseEntity<List<Inventory>> getAllAdjustedInventory() {
        return ResponseEntity.ok(adjustInventoryService.getall());
    }


    @GetMapping("/active-skus-qty")
    public ResponseEntity<List<?>> getAvailableQtyOfAllActiveSkus() {
        List<?> activeSkuQtyList = addInventoryRepo.findAll().stream()
                .filter(inv -> inv.getAvailableQty() > 0)
                .map(inv -> {
                    java.util.Map<String, Object> map = new java.util.HashMap<>();
                    map.put("sku", inv.getSku());
                    map.put("availableQty", inv.getAvailableQty());
                    return map;
                })
                .toList();
        return ResponseEntity.ok(activeSkuQtyList);
    }

    @PostMapping("/skus-qty")
    public ResponseEntity<List<?>> getAvailableQtyOfSpecificSkus(@RequestBody List<String> skuList) {
        List<?> result = addInventoryService.getAvailabilityForSkus(skuList);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/all-products")
    public ResponseEntity<List<ProductFlatResponseDTO>> getFlatProducts() {
        ProductResponseDto response = productService.getAllProducts(); // original full DTO
        List<ProductFlatResponseDTO> flatProducts = productService.getFlatProductList();
        return ResponseEntity.ok(flatProducts);
    }
}
