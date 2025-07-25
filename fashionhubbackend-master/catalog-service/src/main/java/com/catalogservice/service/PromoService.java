package com.catalogservice.service;

import com.catalogservice.dto.PromoRequestDto;
import com.catalogservice.dto.PromoResponseDto;

import java.util.List;

public interface PromoService {
    PromoResponseDto save(PromoRequestDto dto);
    List<PromoResponseDto> getAll();
    PromoResponseDto update(Long id, PromoRequestDto dto);
    void delete(Long id);
    PromoResponseDto updateByPromoCode(String promoCode, PromoRequestDto dto);
    void deleteByPromoCode(String promoCode);
    List<PromoResponseDto> getPromosByProductIds(List<Integer> productIds);
}
