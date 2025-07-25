package com.nisum.cartAndCheckout.service;

import com.nisum.cartAndCheckout.dto.response.InventoryAvailabilityResponseDTO;
import com.nisum.cartAndCheckout.dto.response.OrderResponseDTO;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.exception.InventoryException;
import com.nisum.cartAndCheckout.exception.OrderProcessingException;
import com.nisum.cartAndCheckout.repository.CartItemRepository;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import com.nisum.cartAndCheckout.service.implementation.CheckoutServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckoutServiceImplTest {

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private RestTemplate restTemplate;

    private ShoppingCart cart;
    private List<CartItem> cartItems;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cart = ShoppingCart.builder()
                .cartId(1)
                .userId(101)
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .cartTotal(BigDecimal.valueOf(1000))
                .build();

        CartItem item = CartItem.builder()
                .id(1)
                .productId(201)
                .sku("SKU-RED-M-001")
                .quantity(2)
                .unitPrice(BigDecimal.valueOf(500))
                .discount(BigDecimal.valueOf(25))
                .finalPrice(BigDecimal.valueOf(475))
                .size("M")
                .cart(cart)
                .build();

        cartItems = List.of(item);
    }


//
//    @Test
//    void testPlaceOrder_Success() {
//        when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
//        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);
//
//        InventoryAvailabilityResponseDTO inventoryDTO = new InventoryAvailabilityResponseDTO("SKU-RED-M-001", 10, 1001);
//        ResponseEntity<InventoryAvailabilityResponseDTO[]> inventoryResponse =
//                new ResponseEntity<>(new InventoryAvailabilityResponseDTO[]{inventoryDTO}, HttpStatus.OK);
//        when(restTemplate.postForEntity(anyString(), any(), eq(InventoryAvailabilityResponseDTO[].class)))
//                .thenReturn(inventoryResponse);
//
//        OrderResponseDTO orderResponse = new OrderResponseDTO();
//        orderResponse.setOrderId("ORD123");
//        when(restTemplate.postForObject(anyString(), any(), eq(OrderResponseDTO.class)))
//                .thenReturn(orderResponse);
//
//        ResponseEntity<Void> inventoryUpdateResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(restTemplate.postForEntity(eq("http://localhost:8081/api/inventory/update-stock"), any(), eq(Void.class)))
//                .thenReturn(inventoryUpdateResponse);
//
//        String result = checkoutService.placeOrder(101);
//        assertEquals("ORD123", result);
//    }


    @Test
    void testPlaceOrder_ShoppingCartNotFound() {
        when(shoppingCartRepository.findByUserId(999)).thenReturn(Optional.empty());
        assertThrows(OrderProcessingException.class, () -> checkoutService.placeOrder(999));
    }

    @Test
    void testPlaceOrder_EmptyCartItems() {
        when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCart(cart)).thenReturn(Collections.emptyList());
        assertThrows(OrderProcessingException.class, () -> checkoutService.placeOrder(101));
    }

    @Test
    void testPlaceOrder_InsufficientInventory() {
        when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);

        InventoryAvailabilityResponseDTO inventoryDTO = new InventoryAvailabilityResponseDTO("SKU-RED-M-001", 1, 1001);
        ResponseEntity<InventoryAvailabilityResponseDTO[]> inventoryResponse =
                new ResponseEntity<>(new InventoryAvailabilityResponseDTO[]{inventoryDTO}, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(InventoryAvailabilityResponseDTO[].class)))
                .thenReturn(inventoryResponse);

        assertThrows(InventoryException.class, () -> checkoutService.placeOrder(101));
    }

    @Test
    void testPlaceOrder_InventoryFail() {
        when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);

        when(restTemplate.postForEntity(anyString(), any(), eq(InventoryAvailabilityResponseDTO[].class)))
                .thenThrow(new RestClientException("Inventory Down"));

        assertThrows(InventoryException.class, () -> checkoutService.placeOrder(101));
    }

    @Test
    void testPlaceOrder_OMSUnavailable() {
        when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);

        InventoryAvailabilityResponseDTO inventoryDTO = new InventoryAvailabilityResponseDTO("SKU-RED-M-001", 10, 1001);
        ResponseEntity<InventoryAvailabilityResponseDTO[]> inventoryResponse =
                new ResponseEntity<>(new InventoryAvailabilityResponseDTO[]{inventoryDTO}, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(InventoryAvailabilityResponseDTO[].class)))
                .thenReturn(inventoryResponse);

        // Simulate OMS service failure
        when(restTemplate.postForObject(anyString(), any(), eq(OrderResponseDTO.class)))
                .thenThrow(new RestClientException("OMS Down"));

        assertThrows(OrderProcessingException.class, () -> checkoutService.placeOrder(101));
    }

    @Test
    void testPlaceOrder_OMSReturnsNullResponse() {
        when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);

        InventoryAvailabilityResponseDTO inventoryDTO = new InventoryAvailabilityResponseDTO("SKU-RED-M-001", 10, 1001);
        ResponseEntity<InventoryAvailabilityResponseDTO[]> inventoryResponse =
                new ResponseEntity<>(new InventoryAvailabilityResponseDTO[]{inventoryDTO}, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(InventoryAvailabilityResponseDTO[].class)))
                .thenReturn(inventoryResponse);

        // Simulate OMS returns null
        when(restTemplate.postForObject(anyString(), any(), eq(OrderResponseDTO.class)))
                .thenReturn(null);

        assertThrows(OrderProcessingException.class, () -> checkoutService.placeOrder(101));
    }

//    @Test
//    void testPlaceOrder_OMSReturnsResponseWithNullOrderId() {
//        when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
//        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);
//
//        InventoryAvailabilityResponseDTO inventoryDTO = new InventoryAvailabilityResponseDTO("SKU-RED-M-001", 10, 1001);
//        ResponseEntity<InventoryAvailabilityResponseDTO[]> inventoryResponse =
//                new ResponseEntity<>(new InventoryAvailabilityResponseDTO[]{inventoryDTO}, HttpStatus.OK);
//        when(restTemplate.postForEntity(anyString(), any(), eq(InventoryAvailabilityResponseDTO[].class)))
//                .thenReturn(inventoryResponse);
//
//        // Simulate OMS returns response without orderId
//        OrderResponseDTO orderResponse = new OrderResponseDTO();
//        orderResponse.setOrderId(null);
//        when(restTemplate.postForObject(anyString(), any(), eq(OrderResponseDTO.class)))
//                .thenReturn(orderResponse);
//
//        assertThrows(OrderProcessingException.class, () -> checkoutService.placeOrder(101));
//    }

//    @Test
//    void testPlaceOrder_InventoryUpdateFails() {
//        // Mock shopping cart and items
//        when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
//        when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);
//
//        // Mock inventory availability
//        InventoryAvailabilityResponseDTO inventoryDTO =
//                new InventoryAvailabilityResponseDTO("SKU-RED-M-001", 10, 1001);
//        ResponseEntity<InventoryAvailabilityResponseDTO[]> inventoryResponse =
//                new ResponseEntity<>(new InventoryAvailabilityResponseDTO[]{inventoryDTO}, HttpStatus.OK);
//
//        when(restTemplate.postForEntity(anyString(), any(), eq(InventoryAvailabilityResponseDTO[].class)))
//                .thenReturn(inventoryResponse);
//
//        // Mock OMS response
//        OrderResponseDTO orderResponse = new OrderResponseDTO();
//        orderResponse.setOrderId("ORD123");
//        when(restTemplate.postForObject(anyString(), any(), eq(OrderResponseDTO.class)))
//                .thenReturn(orderResponse);
//
//        // ✅ Correctly mock inventory update failure (exchange is used in service)
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(Void.class)))
//                .thenThrow(new RestClientException("Inventory update failed"));
//
//        // ✅ Assert that InventoryException is thrown
//        assertThrows(InventoryException.class, () -> checkoutService.placeOrder(101));
//    }


}
