package com.nisum.cartAndCheckout.repository;

import com.nisum.cartAndCheckout.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<UserAddress, Integer> {
    List<UserAddress> findByUserId(Integer userId);

    Optional<UserAddress> findByIdAndUserId(Integer addressId, Integer userId);
}
