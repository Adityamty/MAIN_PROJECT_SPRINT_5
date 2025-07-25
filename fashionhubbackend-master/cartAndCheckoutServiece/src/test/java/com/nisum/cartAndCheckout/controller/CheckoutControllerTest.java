package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.service.interfaces.UserAddressService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAddressService userAddressService;

    @MockBean
    private HttpSession session;

//    @Test
//    void testGetAddress_Success() throws Exception {
//        UserAddress mockAddress = new UserAddress();
//        mockAddress.setId(1);
//        mockAddress.setUserId(101);
//
//        when(session.getAttribute("userId")).thenReturn(101);
//        when(userAddressService.getAddressByIdAndUser(1, 101)).thenReturn(mockAddress);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/checkout/address")
//                        .param("id", "1")
//                        .sessionAttr("userId", 101))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1));
//    }

//    @Test
//    void testGetAddress_UserNotLoggedIn() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/checkout/address")
//                        .param("id", "1"))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$.message").value("User not logged in."));
//    }
}
