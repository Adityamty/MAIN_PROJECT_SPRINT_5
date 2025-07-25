package com.nisum.cartAndCheckout.service.implementation;

import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.repository.UserAddressRepository;
import com.nisum.cartAndCheckout.service.interfaces.UserAddressService;
import com.nisum.cartAndCheckout.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressRepository userAddressRepository;

    @Autowired
    public UserAddressServiceImpl(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public UserAddress getAddressByIdAndUser(Integer addressId, Integer userId) {
        return UserValidator.validateUserAddress(
                userAddressRepository.findByIdAndUserId(addressId, userId)
        );
    }
}
