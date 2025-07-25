package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.AddressDto;
import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.service.implementation.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout/address")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<UserAddress> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @PostMapping
    public UserAddress addAddress(@RequestBody AddressDto dto) {
        return addressService.addAddress(dto);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddressById(id);
        return "Success";
    }
}
