package com.nisum.cartAndCheckout.service;

import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.exception.UserNotFoundException;
import com.nisum.cartAndCheckout.repository.UserAddressRepository;
import com.nisum.cartAndCheckout.service.implementation.UserAddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAddressServiceTest {

    @Mock
    private UserAddressRepository userAddressRepository;

    @InjectMocks
    private UserAddressServiceImpl userAddressService;

    private UserAddress mockAddress;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockAddress = new UserAddress();
        mockAddress.setId(1);
        mockAddress.setUserId(101);
        mockAddress.setAddressLane1("Delhi");
        mockAddress.setAddressLane2("123 Street");
        mockAddress.setZipcode("110011");
        mockAddress.setState("Delhi");
        mockAddress.setCountry("India");
    }

    @Test
    void testGetAddressByIdAndUser_Success() {
        when(userAddressRepository.findByIdAndUserId(1, 101)).thenReturn(Optional.of(mockAddress));

        UserAddress result = userAddressService.getAddressByIdAndUser(1, 101);
        assertNotNull(result);
        assertEquals("Delhi", result.getAddressLane1());
        assertEquals("123 Street", result.getAddressLane2());
        verify(userAddressRepository, times(1)).findByIdAndUserId(1, 101);
    }

    @Test
    void testGetAddressByIdAndUser_NotFound() {
        when(userAddressRepository.findByIdAndUserId(1, 101)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userAddressService.getAddressByIdAndUser(1, 101));

        assertEquals("User address not found for the current user.", exception.getMessage());
        verify(userAddressRepository, times(1)).findByIdAndUserId(1, 101);
    }

    @Test
    void testGetAddressByIdAndUser_NullAddressId() {
        Integer addressId = null;
        Integer userId = 101;

        when(userAddressRepository.findByIdAndUserId(null, 101)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                userAddressService.getAddressByIdAndUser(addressId, userId));

        assertEquals("User address not found for the current user.", exception.getMessage());
        verify(userAddressRepository).findByIdAndUserId(null, 101);
    }

    @Test
    void testGetAddressByIdAndUser_NullUserId() {
        Integer addressId = 1;
        Integer userId = null;

        when(userAddressRepository.findByIdAndUserId(1, null)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                userAddressService.getAddressByIdAndUser(addressId, userId));

        assertEquals("User address not found for the current user.", exception.getMessage());
        verify(userAddressRepository).findByIdAndUserId(1, null);
    }

    @Test
    void testGetAddressByIdAndUser_NegativeId() {
        when(userAddressRepository.findByIdAndUserId(-1, -10)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                userAddressService.getAddressByIdAndUser(-1, -10));

        assertEquals("User address not found for the current user.", exception.getMessage());
        verify(userAddressRepository).findByIdAndUserId(-1, -10);
    }

    @Test
    void testGetAddressByIdAndUser_RepositoryThrowsException() {
        when(userAddressRepository.findByIdAndUserId(anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userAddressService.getAddressByIdAndUser(1, 101));

        assertEquals("Database error", exception.getMessage());
        verify(userAddressRepository, times(1)).findByIdAndUserId(1, 101);
    }
}
