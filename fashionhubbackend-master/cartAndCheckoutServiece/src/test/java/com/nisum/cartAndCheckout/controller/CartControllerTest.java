package com.nisum.cartAndCheckout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.CartItemResponseDTO;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartServiceImpl cartServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Positive Test: item added successfully
    @Test
    void testAddToCart_Success() throws Exception {
        CartItemRequestDTO request = CartItemRequestDTO.builder()
                .userId(1)
                .productId(1)
                .sku("sku1")
                .size("M")
                .finalPrice(BigDecimal.valueOf(99.99))
                .build();

        CartItemResponseDTO response = new CartItemResponseDTO("Item added successfully");

        Mockito.when(cartServiceImpl.addToCart(any(CartItemRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item added successfully"));
    }

    // ✅ Negative Test: item already exists in cart
    @Test
    void testAddToCart_ItemAlreadyExists() throws Exception {
        CartItemRequestDTO request = CartItemRequestDTO.builder()
                .userId(2)
                .productId(2)
                .sku("sku2")
                .size("L")
                .finalPrice(BigDecimal.valueOf(49.50))
                .build();

        CartItemResponseDTO response = new CartItemResponseDTO("Item already exists in cart");

        Mockito.when(cartServiceImpl.addToCart(any(CartItemRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item already exists in cart"));
    }
}