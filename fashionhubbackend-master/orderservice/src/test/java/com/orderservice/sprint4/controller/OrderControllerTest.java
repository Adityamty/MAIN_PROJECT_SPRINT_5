//package com.orderservice.sprint4.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.orderservice.sprint4.dto.OrderDetailsRequestDTO;
//import com.orderservice.sprint4.dto.OrderDetailsResponseDTO;
//import com.orderservice.sprint4.service.OrderService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class OrderControllerTest {
//    private MockMvc mockMvc;
//    @Mock
//    private OrderService orderService;
//    @InjectMocks
//    private OrderController orderController;
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
//    }
//
//    @Test
//    void createOrder_success() throws Exception {
//        OrderDetailsRequestDTO dto = new OrderDetailsRequestDTO();
//        when(orderService.createOrderTransaction(any())).thenReturn("INV123");
//        mockMvc.perform(post("/orders/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Order created successfully with invoice number: INV123"));
//    }
//
//    @Test
//    void createOrder_failure() throws Exception {
//        OrderDetailsRequestDTO dto = new OrderDetailsRequestDTO();
//        when(orderService.createOrderTransaction(any())).thenThrow(new RuntimeException("DB error"));
//        mockMvc.perform(post("/orders/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().is5xxServerError())
//                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error Occurred: DB error")));
//    }
//
//    @Test
//    void getOrderDetails_success() throws Exception {
//        OrderDetailsResponseDTO responseDTO = new OrderDetailsResponseDTO();
//        when(orderService.getOrderDetails(1)).thenReturn(responseDTO);
//        mockMvc.perform(get("/orders/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getOrderDetails_notFound() throws Exception {
//        when(orderService.getOrderDetails(1)).thenThrow(new RuntimeException("Not found"));
//        mockMvc.perform(get("/orders/1"))
//                .andExpect(status().isNotFound());
//    }
//}
//
