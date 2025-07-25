package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.CartItemDto;
import com.nisum.cartAndCheckout.dto.response.CartItemResponseDTO;
import com.nisum.cartAndCheckout.dto.response.UpdateCartItemDto;
import com.nisum.cartAndCheckout.dto.response.UpdateCartItemSizeDto;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;
import com.nisum.cartAndCheckout.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/cart")
public class CartController {

    private final CartServiceImpl cartServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    public CartController(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
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

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addToCart(@RequestBody CartItemRequestDTO dto,
                                                        HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            dto.setUserId(userId);
            CartItemResponseDTO response = cartServiceImpl.addToCart(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CartItemResponseDTO("Authentication failed: " + e.getMessage()));
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            List<CartItemDto> items = cartServiceImpl.getCartItemsByUserId(userId);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<UpdateCartItemDto> updateQuantity(
            @RequestParam int cartItemId,
            @RequestParam int newQuantity,
            HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            UpdateCartItemDto response = cartServiceImpl.updateCartItemQuantity(userId, cartItemId, newQuantity);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/updateSize")
    public ResponseEntity<UpdateCartItemSizeDto> updateSize(
            @RequestParam int cartItemId,
            @RequestParam String size,
            HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            UpdateCartItemSizeDto response = cartServiceImpl.updateCartItemSize(userId, cartItemId, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/deleteCartItem")
    public ResponseEntity<Map<String, Object>> deleteItem(
            @RequestParam int cartItemId,
            HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            Map<String, Object> response = cartServiceImpl.deleteCartItem(userId, cartItemId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
