//package com.orderservice.sprint4.service;
//
//import com.orderservice.sprint4.dao.ShipmentItemDAO;
//import com.orderservice.sprint4.dto.InvoiceResponseDTO;
//import com.orderservice.sprint4.dto.ShipmentDetailsResponseDTO;
//import com.orderservice.sprint4.exception.InvoiceNotFoundException;
//import com.orderservice.sprint4.exception.OrderNotFoundException;
//import com.orderservice.sprint4.exception.ShipmentItemsNotFoundException;
//import com.orderservice.sprint4.model.Order;
//import com.orderservice.sprint4.repository.OrderInvoiceRepository;
//import com.orderservice.sprint4.repository.OrderRepository;
//import com.orderservice.sprint4.repository.custom.CustomShipmentItemRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ShipmentServiceImplTest {
//    @Mock
//    private OrderRepository orderRepository;
//    @Mock
//    private OrderInvoiceRepository orderInvoiceRepository;
//    @Mock
//    private CustomShipmentItemRepository customShipmentItemRepository;
//    @InjectMocks
//    private ShipmentServiceImpl shipmentService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getShipmentItemsByOrderId_success() {
//        Integer orderId = 1;
//        Order order = new Order();
//        List<ShipmentItemDAO> items = List.of(new ShipmentItemDAO());
//        InvoiceResponseDTO invoice = new InvoiceResponseDTO();
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//        when(customShipmentItemRepository.getShipmentItemsByOrderId(orderId)).thenReturn(items);
//        when(orderInvoiceRepository.getInvoiceByOrderId(orderId)).thenReturn(invoice);
//        ShipmentDetailsResponseDTO result = shipmentService.getShipmentItemsByOrderId(orderId);
//        assertNotNull(result);
//    }
//
//    @Test
//    void getShipmentItemsByOrderId_orderNotFound() {
//        Integer orderId = 1;
//        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
//        assertThrows(OrderNotFoundException.class, () -> shipmentService.getShipmentItemsByOrderId(orderId));
//    }
//
//    @Test
//    void getShipmentItemsByOrderId_shipmentItemsNotFound() {
//        Integer orderId = 1;
//        Order order = new Order();
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//        when(customShipmentItemRepository.getShipmentItemsByOrderId(orderId)).thenReturn(Collections.emptyList());
//        assertThrows(ShipmentItemsNotFoundException.class, () -> shipmentService.getShipmentItemsByOrderId(orderId));
//    }
//
//    @Test
//    void getShipmentItemsByOrderId_invoiceNotFound() {
//        Integer orderId = 1;
//        Order order = new Order();
//        List<ShipmentItemDAO> items = List.of(new ShipmentItemDAO());
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//        when(customShipmentItemRepository.getShipmentItemsByOrderId(orderId)).thenReturn(items);
//        when(orderInvoiceRepository.getInvoiceByOrderId(orderId)).thenReturn(null);
//        assertThrows(InvoiceNotFoundException.class, () -> shipmentService.getShipmentItemsByOrderId(orderId));
//    }
//}
//
