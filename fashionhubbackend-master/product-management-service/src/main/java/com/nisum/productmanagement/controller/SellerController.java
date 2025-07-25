package com.nisum.productmanagement.controller;

import com.nisum.productmanagement.dto.SellerDto;
import com.nisum.productmanagement.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    
    @Autowired
    private SellerService sellerService;

    @GetMapping
    public List<SellerDto> getAllSellers() {
        return sellerService.getAllSellers();
    }
}
