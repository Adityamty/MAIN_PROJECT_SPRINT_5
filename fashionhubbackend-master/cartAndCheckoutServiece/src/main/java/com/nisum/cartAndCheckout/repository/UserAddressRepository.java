package com.nisum.cartAndCheckout.repository;

import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
    Optional<UserAddress> findByIdAndUserId(Integer id, Integer userId);
}
