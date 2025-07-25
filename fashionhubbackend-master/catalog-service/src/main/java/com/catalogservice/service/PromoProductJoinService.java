package com.catalogservice.service;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;

import java.util.List;

public interface PromoProductJoinService {
    PromoProductJoinResponseDto save(PromoProductJoinRequestDto dto);
    List<PromoProductJoinResponseDto> getAll();
    void delete(Long id);
}
