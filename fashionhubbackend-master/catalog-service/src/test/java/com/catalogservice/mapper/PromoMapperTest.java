package com.catalogservice.mapper;

import com.catalogservice.dto.PromoRequestDto;
import com.catalogservice.dto.PromoResponseDto;
import com.catalogservice.model.Promo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoMapperTest {
    private final PromoMapper mapper = new PromoMapper();

    @Test
    void testToEntity() {
        PromoRequestDto dto = new PromoRequestDto();
        dto.setPromoCode("PROMO1");
        dto.setDescription("desc");
        dto.setAmount(100);
        Promo entity = mapper.toEntity(dto);
        assertEquals("PROMO1", entity.getPromoCode());
        assertEquals("desc", entity.getDescription());
        assertEquals(100, entity.getAmount());
    }

    @Test
    void testToDto() {
        Promo entity = new Promo();
        entity.setPromoCode("PROMO1");
        entity.setDescription("desc");
        entity.setAmount(100);
        PromoResponseDto dto = mapper.toDto(entity);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals("desc", dto.getDescription());
        assertEquals(100, dto.getAmount());
    }
}
