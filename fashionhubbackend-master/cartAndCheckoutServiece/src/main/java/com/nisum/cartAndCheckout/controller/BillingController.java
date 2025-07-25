package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.response.BillResponseDTO;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.exception.OrderProcessingException;
import com.nisum.cartAndCheckout.mapper.BillMapper;
import com.nisum.cartAndCheckout.repository.CartItemRepository;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nisum.cartAndCheckout.constants.AppConstants.CART_NOT_FOUND;
import static com.nisum.cartAndCheckout.constants.AppConstants.EMPTY_CART;

@RestController
@RequestMapping("/api/bill")
public class BillingController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/{userId}")
    public BillResponseDTO generateBill(@PathVariable Integer userId) {

        // 1. Fetch cart
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderProcessingException(CART_NOT_FOUND + userId, null));

        // 2. Fetch cart items
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new OrderProcessingException(EMPTY_CART + userId, null);
        }

        // 3. Build bill response
        BillResponseDTO billResponse = BillMapper.toBillResponseDTO(userId, cartItems);

        // 4. Clear the cart
        cartItemRepository.deleteAll(cartItems);

        return billResponse;
    }
}
