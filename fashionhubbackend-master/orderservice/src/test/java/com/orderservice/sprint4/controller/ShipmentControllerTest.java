//package com.orderservice.sprint4.controller;
//
//import com.orderservice.sprint4.dto.ShipmentDetailsResponseDTO;
//import com.orderservice.sprint4.service.InvoiceService;
//import com.orderservice.sprint4.service.ShipmentService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import jakarta.servlet.http.HttpServletResponse;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class ShipmentControllerTest {
//    private MockMvc mockMvc;
//    @Mock
//    private ShipmentService shipmentService;
//    @Mock
//    private InvoiceService invoiceService;
//    @InjectMocks
//    private ShipmentController shipmentController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(shipmentController).build();
//    }
//
//    @Test
//    void getShipmentItemsByOrderId_success() throws Exception {
//        ShipmentDetailsResponseDTO dto = new ShipmentDetailsResponseDTO();
//        when(shipmentService.getShipmentItemsByOrderId(1)).thenReturn(dto);
//        mockMvc.perform(get("/shipment/shipment-times/order/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void generateInvoicePdf_success() throws Exception {
//        doNothing().when(invoiceService).generatepdf(eq(1), any(HttpServletResponse.class));
//        mockMvc.perform(get("/shipment/generatepdf")
//                .param("orderId", "1")
//                .accept(MediaType.APPLICATION_PDF))
//                .andExpect(status().isOk());
//    }
//}
