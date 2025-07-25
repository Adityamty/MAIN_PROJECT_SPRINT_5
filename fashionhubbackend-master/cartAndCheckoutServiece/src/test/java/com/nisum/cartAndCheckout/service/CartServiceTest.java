package com.nisum.cartAndCheckout.service;

import com.nisum.cartAndCheckout.client.ProductServiceClient;
import com.nisum.cartAndCheckout.dto.response.*;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.exception.InvalidQuantityException;
import com.nisum.cartAndCheckout.exception.ResourceNotFoundException;
import com.nisum.cartAndCheckout.exception.UnauthorizedCartAccessException;
import com.nisum.cartAndCheckout.repository.CartItemRepository;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepo;

    @Mock
    private ShoppingCartRepository cartRepo;

    @Mock
    private ProductServiceClient productClient;

    @InjectMocks
    private CartServiceImpl cartService;

    private ShoppingCart createCartWithItem(Integer userId, CartItem item) {
        ShoppingCart cart = new ShoppingCart();
        cart.setCartId(1);
        cart.setUserId(userId);
        cart.setCreatedDate(LocalDateTime.now());
        cart.setLastUpdatedDate(LocalDateTime.now());
        cart.setCartItems(new ArrayList<>(List.of(item)));
        return cart;
    }

    @Test
    void testGetCartItemsByUserId_Success() {
        Integer userId = 1;

        CartItem item = new CartItem();
        item.setId(10);
        item.setProductId(101);
        item.setSku("SKU001");
        item.setSize("M");
        item.setQuantity(2);
        item.setUnitPrice(BigDecimal.valueOf(100));
        item.setDiscount(BigDecimal.valueOf(10));
        item.setFinalPrice(BigDecimal.valueOf(180));
        item.setIsSavedForLater(false);

        ShoppingCart cart = createCartWithItem(userId, item);
        item.setCart(cart);

        ProductDto productDto = new ProductDto(
                101, "SKU001", "T-Shirt", 5, "ACTIVE",
                LocalDateTime.now(), 1001
        );

        ProductAttributesDto attr = new ProductAttributesDto("SKU001", 101, "M", BigDecimal.valueOf(100), "img.jpg");
        ProductAttributesDto attr2 = new ProductAttributesDto("SKU002", 101, "L", BigDecimal.valueOf(110), "img2.jpg");

        AvailableQuantityDto stock = new AvailableQuantityDto("SKU001", 10);

        when(cartRepo.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(productClient.getProductByProductId(101)).thenReturn(productDto);
        when(productClient.getAttributesByProductIdAndSize(101, "M")).thenReturn(attr);
        when(productClient.getAllAttributesByProductId(101)).thenReturn(List.of(attr, attr2));
        when(productClient.getCategoryBySku("SKU001")).thenReturn(new ProductCategoryDto("SKU001", 10.0));
        when(productClient.getStockQuantityBySku("SKU001")).thenReturn(stock);

        List<CartItemDto> result = cartService.getCartItemsByUserId(userId);

        assertEquals(1, result.size());
        assertEquals("T-Shirt", result.get(0).getProductName());
        assertEquals("M", result.get(0).getSize());
        assertEquals(2, result.get(0).getCartQuantity());
        assertEquals(10, result.get(0).getStockQuantity());
    }

    @Test
    void testUpdateCartItemQuantity_Success() {
        Integer userId = 1;
        Integer cartItemId = 10;

        CartItem item = new CartItem();
        item.setId(cartItemId);
        item.setQuantity(1);
        item.setSku("SKU001");
        item.setUnitPrice(BigDecimal.valueOf(100));
        item.setDiscount(BigDecimal.valueOf(10));
        item.setFinalPrice(BigDecimal.valueOf(90));

        ShoppingCart cart = createCartWithItem(userId, item);
        item.setCart(cart);

        AvailableQuantityDto stock = new AvailableQuantityDto("SKU001", 20);

        when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.of(item));
        when(productClient.getStockQuantityBySku("SKU001")).thenReturn(stock);
        when(cartRepo.save(any())).thenReturn(cart);
        when(cartItemRepo.save(any())).thenReturn(item);

        UpdateCartItemDto result = cartService.updateCartItemQuantity(userId, cartItemId, 2);

        assertEquals("updated", result.getMessage());
        assertEquals(20, result.getStockQuantity());
    }

    @Test
    void testUpdateCartItemQuantity_Invalid() {
        assertThrows(InvalidQuantityException.class,
                () -> cartService.updateCartItemQuantity(1, 10, 0));
    }

    @Test
    void testUpdateCartItemQuantity_Unauthorized() {
        CartItem item = new CartItem();
        ShoppingCart cart = createCartWithItem(2, item);
        item.setCart(cart);

        when(cartItemRepo.findById(1)).thenReturn(Optional.of(item));

        assertThrows(UnauthorizedCartAccessException.class,
                () -> cartService.updateCartItemQuantity(1, 1, 2));
    }

    @Test
    void testDeleteCartItem_Success() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setFinalPrice(BigDecimal.valueOf(90));

        ShoppingCart cart = createCartWithItem(1, item);
        item.setCart(cart);

        when(cartItemRepo.findById(1)).thenReturn(Optional.of(item));

        Map<String, Object> result = cartService.deleteCartItem(1, 1);

        assertEquals("deleted", result.get("message"));
        verify(cartItemRepo).delete(item);
    }

    @Test
    void testUpdateCartItemSize_Success() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setProductId(101);
        item.setQuantity(2);
        item.setSku("OLD-SKU");

        ShoppingCart cart = createCartWithItem(1, item);
        item.setCart(cart);

        ProductAttributesDto attrDto = new ProductAttributesDto("SKU999", 101, "L", BigDecimal.valueOf(60), "img.png");
        ProductCategoryDto categoryDto = new ProductCategoryDto("SKU999", 5.0);
        AvailableQuantityDto stockDto = new AvailableQuantityDto("SKU999", 50);

        when(cartItemRepo.findById(1)).thenReturn(Optional.of(item));
        when(productClient.getAttributesByProductIdAndSize(101, "L")).thenReturn(attrDto);
        when(productClient.getCategoryBySku("SKU999")).thenReturn(categoryDto);
        when(productClient.getStockQuantityBySku("SKU999")).thenReturn(stockDto);
        when(cartRepo.save(any())).thenReturn(cart);

        UpdateCartItemSizeDto result = cartService.updateCartItemSize(1, 1, "L");

        assertEquals("Size Updated", result.getMessage());
        assertEquals(50, result.getStockQuantity());
        assertEquals(60.0, result.getUnitPrice());
        assertEquals(5.0, result.getDiscount());
        assertEquals(110.0, result.getFinalPrice());
    }

    @Test
    void testUpdateCartItemSize_Unauthorized() {
        CartItem item = new CartItem();
        ShoppingCart cart = createCartWithItem(2, item);
        item.setCart(cart);

        when(cartItemRepo.findById(1)).thenReturn(Optional.of(item));

        assertThrows(UnauthorizedCartAccessException.class,
                () -> cartService.updateCartItemSize(1, 1, "M"));
    }

    @Test
    void testUpdateCartItemSize_AttributeNotFound() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setProductId(101);

        ShoppingCart cart = createCartWithItem(1, item);
        item.setCart(cart);

        when(cartItemRepo.findById(1)).thenReturn(Optional.of(item));
        when(productClient.getAttributesByProductIdAndSize(101, "XL")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> cartService.updateCartItemSize(1, 1, "XL"));
    }
}
