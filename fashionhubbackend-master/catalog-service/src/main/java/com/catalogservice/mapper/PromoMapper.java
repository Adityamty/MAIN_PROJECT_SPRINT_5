package com.catalogservice.mapper;

import com.catalogservice.dto.PromoRequestDto;
import com.catalogservice.dto.PromoResponseDto;
import com.catalogservice.model.Promo;
import org.springframework.stereotype.Component;

@Component
public class PromoMapper {

    public Promo toEntity(PromoRequestDto dto) {
        Promo entity = new Promo();
        entity.setPromoCode(dto.getPromoCode());
        entity.setPromoType(dto.getPromoType());
        entity.setDescription(dto.getDescription());
        entity.setAmount(dto.getAmount());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public PromoResponseDto toDto(Promo entity) {
        PromoResponseDto dto = new PromoResponseDto();
        dto.setPromoCode(entity.getPromoCode());
        dto.setPromoType(entity.getPromoType());
        dto.setDescription(entity.getDescription());
        dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
