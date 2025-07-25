package com.nisum.cartAndCheckout.service.interfaces;

import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.CartItemResponseDTO;

public interface CartServiceInterface {
    CartItemResponseDTO addToCart(CartItemRequestDTO dto);
}
