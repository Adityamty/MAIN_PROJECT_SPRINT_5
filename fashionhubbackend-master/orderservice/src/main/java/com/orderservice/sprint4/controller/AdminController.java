package com.orderservice.sprint4.controller;

import com.orderservice.sprint4.dto.OrderItemStatusRequestDTO;
import com.orderservice.sprint4.dto.OrderStatusRequestDTO;
import com.orderservice.sprint4.dto.ShipmentItemListDTO;
import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import com.orderservice.sprint4.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PatchMapping("/order/shipped")
    public  void orderShipped(@RequestBody OrderItemStatusRequestDTO dto){
        adminService.orderShipped(dto);
    }

    @PatchMapping("/order/delivered")
    public void orderDelivered(@RequestBody OrderItemStatusRequestDTO dto){
        adminService.orderDelivered(dto);
    }

    @PatchMapping("/order/cancel")
    public void orderCancilatioin(@RequestBody OrderItemStatusRequestDTO dto){adminService.orderCanelled(dto);}

    @GetMapping("/orders/get")
    public ResponseEntity<List<ShipmentItemListDTO>> getOrdersList(@RequestParam ShipmentItemStatus itemStatus) {
        List<ShipmentItemListDTO> shipmentList = adminService.getShipmentByStatus(itemStatus);
        return shipmentList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(shipmentList);
    }
}
