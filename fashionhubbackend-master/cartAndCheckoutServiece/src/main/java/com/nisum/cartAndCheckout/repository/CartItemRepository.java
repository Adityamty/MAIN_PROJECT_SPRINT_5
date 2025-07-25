package com.nisum.cartAndCheckout.repository;

import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCart_UserId(Integer userId);

    List<CartItem> findByCart(ShoppingCart cart);
}
