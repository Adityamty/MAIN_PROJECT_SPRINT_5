package com.nisum.cartAndCheckout.service.interfaces;

import com.nisum.cartAndCheckout.entity.UserAddress;

public interface UserAddressService {
    UserAddress getAddressByIdAndUser(Integer addressId, Integer userId);
}
