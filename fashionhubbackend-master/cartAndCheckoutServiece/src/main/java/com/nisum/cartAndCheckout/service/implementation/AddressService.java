package com.nisum.cartAndCheckout.service.implementation;

import com.nisum.cartAndCheckout.dto.AddressDto;
import com.nisum.cartAndCheckout.exception.AddressNotFoundException;
import com.nisum.cartAndCheckout.exception.UserNotFoundException;
import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private HttpSession session;

    private Integer getUserIdFromSession() {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
             session.setAttribute("userId", 1);
             userId=1;
            //throw new UserNotLoggedInException("User not logged in.");
        }
        return (Integer) userId;
    }

    public List<UserAddress> getAllAddresses() {
        Integer userId = getUserIdFromSession();
        return addressRepository.findByUserId(userId);
    }

    public UserAddress addAddress(AddressDto dto) {
        Integer userId = getUserIdFromSession();
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        address.setAddressLane1(dto.getAddressLane1());
        address.setAddressLane2(dto.getAddressLane2());
        address.setZipcode(dto.getZipcode());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setAddressType(dto.getAddressType());
        address.setContactName(dto.getContactName());
        address.setContactPhoneNumber(dto.getContactPhoneNumber());
        return addressRepository.save(address);
    }

    public void deleteAddressById(Integer id) {
        Integer userId = getUserIdFromSession();
        Optional<UserAddress> address = addressRepository.findByIdAndUserId(id, userId);
        if (address.isPresent()) {
            addressRepository.delete(address.get());
        } else {
            throw new AddressNotFoundException("User address not found for the current user");
        }
    }
}
