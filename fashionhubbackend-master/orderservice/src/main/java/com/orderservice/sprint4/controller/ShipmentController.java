package com.orderservice.sprint4.controller;

import com.orderservice.sprint4.dto.ShipmentDetailsResponseDTO;
import com.orderservice.sprint4.model.ShipmentItem;
import com.orderservice.sprint4.service.InvoiceService;
import com.orderservice.sprint4.service.ShipmentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/shipment")
@CrossOrigin("*")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/shipment-times/order/{orderId}")
    private ResponseEntity<?> getShipmentItemsByOrderId(@PathVariable Integer orderId){
        ShipmentDetailsResponseDTO responseDTO = shipmentService.getShipmentItemsByOrderId(orderId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(value="/generatepdf",produces = "application/pdf")
    public void generateInvoicePdf(@RequestParam("orderId") int orderId, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("content-Disposition","attachment; filename=order_" + orderId + ".pdf");
        invoiceService.generatepdf(orderId,response);
    }
}
