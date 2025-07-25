package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.exception.OrderProcessingException;
import com.nisum.cartAndCheckout.service.interfaces.CheckoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckoutService checkoutService;

    @Test
    void testPlaceOrder_Success() throws Exception {
        // Mock session with user ID
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 101);

        when(checkoutService.placeOrder(101)).thenReturn("ORD123");

        mockMvc.perform(post("/api/order/place").session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Order placed successfully. Order ID: ORD123")));
    }

    @Test
    void testPlaceOrder_UserNotLoggedIn() throws Exception {
        // No userId in session
        mockMvc.perform(post("/api/order/place"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("User not logged in."))
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }

    @Test
    void testPlaceOrder_ExceptionFromService() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 101);

        when(checkoutService.placeOrder(101))
                .thenThrow(new OrderProcessingException("No items in cart", null));

        mockMvc.perform(post("/api/order/place").session(session))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No items in cart"))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}

