package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.PromoDTO;
import com.nisum.cartAndCheckout.exception.PromoServiceUnavailableException;
import com.nisum.cartAndCheckout.service.interfaces.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class PromoController {

    private final PromoService promoService;

    @Autowired
    public PromoController(PromoService promoService) {
        this.promoService = promoService;
    }

    @PostMapping("/promos")
    public ResponseEntity<List<PromoDTO>> getPromosForProducts(@RequestBody List<Integer> productIds) {
        try {
            List<PromoDTO> result = promoService.getPromosForProducts(productIds);
            return ResponseEntity.ok(result);
        } catch (PromoServiceUnavailableException e) {
            return ResponseEntity.status(500).body(null);  // Change from 503 to 500
        }
    }

}
