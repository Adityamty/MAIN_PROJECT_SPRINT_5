package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.service.interfaces.UserAddressService;
import com.nisum.cartAndCheckout.validation.UserValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final UserAddressService userAddressService;

    @Autowired
    public CheckoutController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @GetMapping("/checkout-address")
    public ResponseEntity<UserAddress> getCheckoutAddress(@RequestParam("id") Integer addressId,
                                                          HttpSession session) {
        // 1. Validate and fetch userId from session
        Integer userId = UserValidator.getValidatedUserId(session);

        // 2. Fetch the address associated with userId and addressId
        UserAddress address = userAddressService.getAddressByIdAndUser(addressId, userId);

        // 3. Return the response
        return ResponseEntity.ok(address);
    }

}
