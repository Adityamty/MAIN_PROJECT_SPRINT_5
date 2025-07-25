package com.catalogservice.mapper;

import com.catalogservice.dto.PromoRequestDto;
import com.catalogservice.dto.PromoResponseDto;
import com.catalogservice.model.Promo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoProductJoinMapperTest {
    private final PromoProductJoinMapper mapper = new PromoProductJoinMapper();

    @Test
    void testToEntity() {
        com.catalogservice.dto.PromoProductJoinRequestDto dto = new com.catalogservice.dto.PromoProductJoinRequestDto();
        dto.setPromoCode("PROMO1");
        dto.setProductId(123L);
        com.catalogservice.model.PromoProductJoin entity = mapper.toEntity(dto);
        assertEquals("PROMO1", entity.getPromoCode());
        assertEquals(123L, entity.getProductId());
    }

    @Test
    void testToDto() {
        com.catalogservice.model.PromoProductJoin entity = new com.catalogservice.model.PromoProductJoin();
        entity.setPromoCode("PROMO1");
        entity.setProductId(123L);
        com.catalogservice.dto.PromoProductJoinResponseDto dto = mapper.toDto(entity);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals(123L, dto.getProductId());
    }
}
