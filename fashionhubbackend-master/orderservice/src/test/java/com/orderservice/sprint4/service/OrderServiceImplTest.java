//package com.orderservice.sprint4.service;
//
//import com.itextpdf.text.pdf.BidiLine;
//import com.orderservice.sprint4.dto.OrderDetailsRequestDTO;
//import com.orderservice.sprint4.model.Order;
//import com.orderservice.sprint4.repository.OrderInvoiceRepository;
//import com.orderservice.sprint4.repository.OrderItemRepository;
//import com.orderservice.sprint4.repository.OrderRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class OrderServiceImplTest {
//    @Mock
//    private OrderRepository orderRepository;
//    @Mock
//    private OrderInvoiceRepository orderInvoiceRepository;
//    @Mock
//    private OrderItemRepository orderItemRepository;
//    @InjectMocks
//    private OrderServiceImpl orderService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateOrderTransaction_success() {
//        OrderDetailsRequestDTO dto = mock(OrderDetailsRequestDTO.class);
//        when(dto.getPaymentMode()).thenReturn("CASH");
//        when(dto.getUserId()).thenReturn(1);
//        when(dto.getOrderItemRequestDTOS()).thenReturn(new java.util.ArrayList<>());
//        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
//        when(orderInvoiceRepository.save(any())).thenReturn(null);
//        when(orderItemRepository.saveAll(any())).thenReturn(new java.util.ArrayList<>());
//        String invoiceNumber = orderService.createOrderTransaction(dto);
//        assertNotNull(invoiceNumber);
//    }
//
//    @Test
//    void testCreateOrderTransaction_failure() {
//        OrderDetailsRequestDTO dto = mock(OrderDetailsRequestDTO.class);
//        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException("DB error"));
//        assertThrows(RuntimeException.class, () -> orderService.createOrderTransaction(dto));
//    }
//}
