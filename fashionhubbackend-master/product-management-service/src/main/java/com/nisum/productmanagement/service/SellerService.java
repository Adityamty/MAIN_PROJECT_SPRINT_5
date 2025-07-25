package com.nisum.productmanagement.service;

import com.nisum.productmanagement.dto.SellerDto;
import com.nisum.productmanagement.model.Seller;
import com.nisum.productmanagement.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    public List<SellerDto> getAllSellers() {
        return sellerRepository.findAll().stream()
            .map(s -> new SellerDto(
                s.getId(),
                s.getSellerName(),
                s.getContactName(),
                s.getEmail(),
                s.getPhoneNumber(),
                s.getAddressLine1(),
                s.getAddressLine2(),
                s.getCity(),
                s.getState(),
                s.getZipCode(),
                s.getCountry()
            ))
            .collect(Collectors.toList());
    }
}
