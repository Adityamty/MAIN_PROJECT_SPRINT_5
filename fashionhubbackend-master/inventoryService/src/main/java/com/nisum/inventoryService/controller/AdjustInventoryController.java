//package com.nisum.inventoryService.controller;
//
//import com.nisum.inventoryService.dao.InventoryDao;
//import com.nisum.inventoryService.dto.AdjustRequest;
//import com.nisum.inventoryService.service.AdjustInventoryService;
//import com.nisum.inventoryService.utils.JsonResponseUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/inventory")
//@CrossOrigin(origins = "http://localhost:5173")
//public class AdjustInventoryController {
//
//    @Autowired
//    private AdjustInventoryService inventoryService;
//
//    // ✅ Adjust inventory and return updated InventoryDao as JSON
//    @PutMapping("/adjust")
//    public ResponseEntity<?> adjustInventory(@RequestBody AdjustRequest request) {
//        InventoryDao updated = inventoryService.adjustInventory(request.getSku(), request.getQuantity());
//        if (updated != null) {
//            return ResponseEntity.ok(updated);
//        } else {
//            Map<String, String> errorResponse = JsonResponseUtil.createError("SKU not found");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//    }
//
//    // ✅ Cancel inventory and return updated InventoryDao as JSON
//    @PutMapping("/cancel")
//    public ResponseEntity<?> cancelInventory(@RequestBody AdjustRequest request) {
//        InventoryDao updated = inventoryService.cancelInventory(request.getSku(), request.getQuantity());
//        if (updated != null) {
//            return ResponseEntity.ok(updated);
//        } else {
//            Map<String, String> errorResponse = JsonResponseUtil.createError("SKU not found");
//            return ResponseEntity.status(404).body(errorResponse);
//        }
//    }
//
//    // ✅ Return all inventory as JSON list
//    @GetMapping("/all")
//    public ResponseEntity<List<InventoryDao>> getAllInventory() {
//        List<InventoryDao> inventoryList = inventoryService.getall();
//        return ResponseEntity.ok(inventoryList);
//    }
//}
