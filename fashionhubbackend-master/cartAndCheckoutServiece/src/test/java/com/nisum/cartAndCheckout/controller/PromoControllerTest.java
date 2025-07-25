package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.PromoDTO;
import com.nisum.cartAndCheckout.exception.PromoServiceUnavailableException;
import com.nisum.cartAndCheckout.service.interfaces.PromoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class PromoControllerTest {

    @InjectMocks
    private PromoController promoController;

    @Mock
    private PromoService promoService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(promoController).build();
    }

    @Test
    void testGetPromosForProducts_Success() throws Exception {
        List<Integer> productIds = Arrays.asList(1, 2);

        List<PromoDTO> promoList = Arrays.asList(
                new PromoDTO("PROMO10", 10),
                new PromoDTO("SAVE20", 20)
        );

        when(promoService.getPromosForProducts(productIds)).thenReturn(promoList);

        mockMvc.perform(post("/api/cart/promos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productIds)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(promoService, times(1)).getPromosForProducts(productIds);
    }

    @Test
    void testGetPromosForProducts_EmptyList() throws Exception {
        List<Integer> productIds = Collections.emptyList();

        when(promoService.getPromosForProducts(productIds))
                .thenThrow(new PromoServiceUnavailableException("No promos found"));

        mockMvc.perform(post("/api/cart/promos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productIds)))
                .andExpect(status().isInternalServerError());

        verify(promoService, times(1)).getPromosForProducts(productIds);
    }

    @Test
    void testGetPromosForProducts_NullList() throws Exception {
        mockMvc.perform(post("/api/cart/promos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest()); // Expecting validation failure or default exception
    }

    @Test
    void testGetPromosForProducts_ServiceThrowsException() throws Exception {
        List<Integer> productIds = Arrays.asList(101, 102);

        when(promoService.getPromosForProducts(productIds))
                .thenThrow(new PromoServiceUnavailableException("Service unavailable"));

        mockMvc.perform(post("/api/cart/promos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productIds)))
                .andExpect(status().isInternalServerError());

        verify(promoService, times(1)).getPromosForProducts(productIds);
    }
}
