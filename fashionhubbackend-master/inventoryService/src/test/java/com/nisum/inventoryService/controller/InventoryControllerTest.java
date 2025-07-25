package com.nisum.inventoryService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dto.AdjustRequest;
import com.nisum.inventoryService.dto.InventoryDTO;
import com.nisum.inventoryService.dto.ReserveRequest;
import com.nisum.inventoryService.service.AddInventoryService;
import com.nisum.inventoryService.service.AdjustInventoryService;
import com.nisum.inventoryService.service.InventoryService;
import com.nisum.inventoryService.repository.AddInventoryRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @MockBean
    private AdjustInventoryService adjustInventoryService;

    @MockBean
    private AddInventoryService addInventoryService;

    @MockBean
    private AddInventoryRepo addInventoryRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAvailableQuantity_skuNotFound() throws Exception {
        when(addInventoryRepo.findBySku("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/inventory/available/unknown"))
                .andExpect(status().isOk())
                .andExpect(content().string("sku does not exist"));
    }

    // -------------------- POST /reserve --------------------

    @Test
    void testReserveInventory_success() throws Exception {
        ReserveRequest request = new ReserveRequest("sku1", 5, 1, 12);
        doNothing().when(inventoryService).reserveInventory(any());

        mockMvc.perform(post("/api/inventory/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventory reserved successfully"));
    }

    // -------------------- PUT /adjust --------------------

    @Test
    void testAdjustInventory_success() throws Exception {
        Inventory updated = new Inventory("sku1", "wareHouse1", 30, 1);
        AdjustRequest request = new AdjustRequest("sku1", 10);

        when(adjustInventoryService.adjustInventory("sku1", 10)).thenReturn(updated);

        mockMvc.perform(put("/api/inventory/adjust")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Adjusted quantity. New availableQty: 30"));
    }

    @Test
    void testAdjustInventory_notFound() throws Exception {
        AdjustRequest request = new AdjustRequest("sku1", 10);

        when(adjustInventoryService.adjustInventory("sku1", 10)).thenReturn(null);

        mockMvc.perform(put("/api/inventory/adjust")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("SKU not found"));
    }

    // -------------------- DELETE /{id} --------------------

    @Test
    void testDeleteInventory_success() throws Exception {
        doNothing().when(inventoryService).deleteInventory(1);

        mockMvc.perform(delete("/api/inventory/1"))
                .andExpect(status().isNoContent());
    }

}
