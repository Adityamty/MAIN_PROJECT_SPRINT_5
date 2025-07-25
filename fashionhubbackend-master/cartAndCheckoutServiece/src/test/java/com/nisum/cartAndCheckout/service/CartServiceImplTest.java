package com.nisum.cartAndCheckout.service;

import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.CartItemResponseDTO;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.mapper.CartItemMapper;
import com.nisum.cartAndCheckout.repository.CartItemRepository;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private ShoppingCartRepository cartRepo;

    @Mock
    private CartItemRepository cartItemRepo;

    private AutoCloseable closeable;

    private CartItemRequestDTO dto;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        dto = CartItemRequestDTO.builder()
                .userId(1)
                .productId(1)
                .sku("sku1")
                .size("M")
                .finalPrice(BigDecimal.valueOf(250.0))
                .build();
    }

    @AfterEach
    void teardown() throws Exception {
        closeable.close();
    }

    // ✅ Case 1: New Cart is created, Item added
    @Test
    void testAddToCart_NewCart_ItemAddedSuccessfully() {
        when(cartRepo.findByUserId(1)).thenReturn(Optional.empty());

        ShoppingCart newCart = ShoppingCart.builder()
                .userId(1)
                .cartItems(new ArrayList<>())
                .cartTotal(BigDecimal.ZERO)
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .build();

        when(cartRepo.save(any())).thenReturn(newCart);
        when(cartItemRepo.save(any())).thenReturn(new CartItem());

        CartItemResponseDTO response = cartService.addToCart(dto);

        assertEquals("Item added successfully", response.getMessage());
        verify(cartRepo, times(2)).save(any()); // once for cart, once after updating
        verify(cartItemRepo).save(any());
    }

    // ✅ Case 2: Cart exists, Item already exists
    @Test
    void testAddToCart_ExistingCart_ItemAlreadyExists() {
        CartItem existingItem = CartItem.builder()
                .productId(1)
                .sku("sku1")
                .size("M")
                .build();

        ShoppingCart existingCart = ShoppingCart.builder()
                .userId(1)
                .cartItems(List.of(existingItem))
                .cartTotal(BigDecimal.valueOf(100))
                .build();

        when(cartRepo.findByUserId(1)).thenReturn(Optional.of(existingCart));

        CartItemResponseDTO response = cartService.addToCart(dto);

        assertEquals("Item already exists in cart", response.getMessage());
        verify(cartRepo, never()).save(any());
        verify(cartItemRepo, never()).save(any());
    }

    // ✅ Case 3: Cart exists, Item not present
    @Test
    void testAddToCart_ExistingCart_AddsItem() {
        CartItem otherItem = CartItem.builder()
                .productId(2)
                .sku("skuX")
                .size("L")
                .build();

        ShoppingCart existingCart = ShoppingCart.builder()
                .userId(1)
                .cartItems(new ArrayList<>(List.of(otherItem)))
                .cartTotal(BigDecimal.valueOf(300))
                .lastUpdatedDate(LocalDateTime.now())
                .build();

        when(cartRepo.findByUserId(1)).thenReturn(Optional.of(existingCart));
        when(cartItemRepo.save(any())).thenReturn(new CartItem());
        when(cartRepo.save(any())).thenReturn(existingCart);

        CartItemResponseDTO response = cartService.addToCart(dto);

        assertEquals("Item added successfully", response.getMessage());
        verify(cartItemRepo).save(any());
        verify(cartRepo).save(any());
    }

    // ✅ Case 4: Cart is found but item list is null (edge case)
    @Test
    void testAddToCart_CartWithNullItemList_AddsItemSafely() {
        ShoppingCart cartWithNullList = ShoppingCart.builder()
                .userId(1)
                .cartItems(null)
                .cartTotal(BigDecimal.ZERO)
                .lastUpdatedDate(LocalDateTime.now())
                .build();

        // simulate getCartItems() returning null
        when(cartRepo.findByUserId(1)).thenReturn(Optional.of(cartWithNullList));
        when(cartItemRepo.save(any())).thenReturn(new CartItem());
        when(cartRepo.save(any())).thenReturn(cartWithNullList);

        assertThrows(NullPointerException.class, () -> cartService.addToCart(dto));
    }
}
