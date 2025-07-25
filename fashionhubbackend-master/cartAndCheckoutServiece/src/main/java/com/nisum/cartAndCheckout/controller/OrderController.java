package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.exception.UserNotFoundException;
import com.nisum.cartAndCheckout.service.interfaces.CheckoutService;
import com.nisum.cartAndCheckout.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final CheckoutService checkoutService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public OrderController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // Helper method to get userId from JWT token
    private Integer getUserIdFromToken(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtil.validateJwtToken(token)) {
            return jwtUtil.getUserIdFromToken(token);
        }
        throw new RuntimeException("Invalid or missing authentication token");
    }

    // Helper method to extract token from request
    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            String orderId = checkoutService.placeOrder(userId);
            return ResponseEntity.ok().body("Order placed successfully. Order ID: " + orderId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }
}
