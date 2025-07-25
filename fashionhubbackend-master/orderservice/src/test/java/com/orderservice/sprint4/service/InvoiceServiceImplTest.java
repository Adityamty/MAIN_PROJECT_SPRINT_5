//package com.orderservice.sprint4.service;
//
//import com.orderservice.sprint4.model.Order;
//import com.orderservice.sprint4.model.OrderInvoice;
//import com.orderservice.sprint4.model.OrderItem;
//import com.orderservice.sprint4.repository.OrderInvoiceRepository;
//import com.orderservice.sprint4.repository.OrderItemRepository;
//import com.orderservice.sprint4.repository.OrderRepository;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//class InvoiceServiceImplTest {
//    @Mock
//    private OrderRepository orderRepository;
//    @Mock
//    private OrderInvoiceRepository orderInvoiceRepository;
//    @Mock
//    private OrderItemRepository orderItemRepository;
//    @Mock
//    private HttpServletResponse response;
//    @InjectMocks
//    private InvoiceServiceImpl invoiceService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void generatepdf_success() throws Exception {
//        Order order = mock(Order.class);
//        OrderInvoice invoice = mock(OrderInvoice.class);
//        OrderItem orderItem = mock(OrderItem.class);
//        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
//        when(order.getOrderItems()).thenReturn(List.of(orderItem));
//        when(order.getOrderInvoice()).thenReturn(invoice);
//        when(invoice.getInvoiceNumber()).thenReturn("INV123");
//        when(invoice.getInvoiceDate()).thenReturn(java.time.LocalDateTime.now());
//        when(invoice.getInvoiceAmount()).thenReturn(java.math.BigDecimal.ONE);
//        when(invoice.getPaymentMode()).thenReturn("CASH");
//        when(order.getOrderTotal()).thenReturn(java.math.BigDecimal.TEN);
//        when(order.getPromoDiscount()).thenReturn(java.math.BigDecimal.ZERO);
//        when(orderItem.getOrderItemId()).thenReturn(1);
//        when(orderItem.getProductId()).thenReturn(1);
//        when(orderItem.getSku()).thenReturn("SKU1");
//        when(orderItem.getQuantity()).thenReturn(1);
//        when(orderItem.getUnitPrice()).thenReturn(java.math.BigDecimal.ONE);
//        when(orderItem.getDiscount()).thenReturn(java.math.BigDecimal.ZERO);
//        when(orderItem.getFinalPrice()).thenReturn(java.math.BigDecimal.ONE);
//        when(orderItem.getSize()).thenReturn("M");
//        when(orderItem.getStatus()).thenReturn("DELIVERED");
//        when(orderItem.getSellerId()).thenReturn(1);
//        ServletOutputStream servletOutputStream = new ServletOutputStream() {
//            @Override
//            public void write(int b) throws IOException {
//                // no-op for test
//            }
//            @Override
//            public void setWriteListener(jakarta.servlet.WriteListener writeListener) {
//                // no-op for test
//            }
//            @Override
//            public boolean isReady() {
//                return true;
//            }
//        };
//        when(response.getOutputStream()).thenReturn(servletOutputStream);
//        invoiceService.generatepdf(1, response);
//        verify(orderRepository).findById(1);
//        verify(response).getOutputStream();
//    }
//
//    @Test
//    void generatepdf_orderNotFound() {
//        when(orderRepository.findById(1)).thenReturn(Optional.empty());
//        assertThrows(RuntimeException.class, () -> invoiceService.generatepdf(1, response));
//    }
//}
