//package com.nisum.inventoryService.controller;
//
//import com.nisum.inventoryService.dto.InventoryDto;
//import com.nisum.inventoryService.repository.AddInventoryRepo;
//import com.nisum.inventoryService.service.AddInventoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/inventory")
//
//@CrossOrigin(origins = "*", allowedHeaders = "*",
//        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
//public class AddInventoryController {
//
//    @Autowired
//    private AddInventoryService addInventoryService;
//
//    @Autowired
//    private AddInventoryRepo addInventoryRepo;
//
//    @PostMapping("/add")
//    public ResponseEntity<String> addInventory(@RequestBody InventoryDto inventoryDto) {
//        try {
//            addInventoryService.addInventory(inventoryDto);
//            return ResponseEntity.ok("{\"message\": \"Inventory added successfully\"}");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body("{\"error\": \"Database insert failed\"}");
//        }
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<InventoryDto>> getAllInventory() {
//        return ResponseEntity.ok(addInventoryService.getAllInventory());
//    }
//
//    @GetMapping("/available/{sku}")
//    public ResponseEntity<String> getAvailableQuantity(@PathVariable String sku) {
//        return addInventoryRepo.findBySku(sku)
//                .map(inventory -> {
//                    int qty = inventory.getAvailableQty();
//                    if (qty == 0) {
//                        return ResponseEntity.ok("out of stock");
//                    } else {
//                        return ResponseEntity.ok(String.valueOf(qty));
//                    }
//                })
//                .orElseGet(() -> ResponseEntity.ok("sku does not exist"));
//    }
//
//
//    @GetMapping("/active-skus-qty")
//    public ResponseEntity<List<?>> getAvailableQtyOfAllActiveSkus() {
//        List<?> activeSkuQtyList = addInventoryRepo.findAll().stream()
//                .filter(inv -> inv.getAvailableQty() > 0)
//                .map(inv -> {
//                    java.util.Map<String, Object> map = new java.util.HashMap<>();
//                    map.put("sku", inv.getSku());
//                    map.put("availableQty", inv.getAvailableQty());
//                    return map;
//                })
//                .toList();
//        return ResponseEntity.ok(activeSkuQtyList);
//    }
//
//    // You can use GET with request param for a list of SKUs, e.g. /api/inventory/skus-qty?sku=sku1&sku=sku2
//    @PostMapping("/skus-qty")
//    public ResponseEntity<List<?>> getAvailableQtyOfSpecificSkus(@RequestBody List<String> skuList) {
//        List<?> result = addInventoryService.getAvailabilityForSkus(skuList);
//        return ResponseEntity.ok(result);
//    }
//}
