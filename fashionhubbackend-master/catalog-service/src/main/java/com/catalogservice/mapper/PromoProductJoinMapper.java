package com.catalogservice.mapper;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
import com.catalogservice.model.PromoProductJoin;
import org.springframework.stereotype.Component;

@Component
public class PromoProductJoinMapper {

    public PromoProductJoin toEntity(PromoProductJoinRequestDto dto) {
        PromoProductJoin entity = new PromoProductJoin();
        entity.setPromoCode(dto.getPromoCode());
        entity.setProductId(dto.getProductId());
        return entity;
    }

    public PromoProductJoinResponseDto toDto(PromoProductJoin entity) {
        PromoProductJoinResponseDto dto = new PromoProductJoinResponseDto();
        dto.setPromoCode(entity.getPromoCode());
        dto.setProductId(entity.getProductId());
        return dto;
    }
}
