package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoProductJoinResponseDtoTest {
    @Test
    void testSettersAndGetters() {
        PromoProductJoinResponseDto dto = new PromoProductJoinResponseDto();
        dto.setPromoCode("PROMO1");
        dto.setProductId(123L);
        dto.setJoinId(1L);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals(123L, dto.getProductId());
        assertEquals(1L, dto.getJoinId());
    }

    @Test
    void testEqualsAndHashCode() {
        PromoProductJoinResponseDto dto1 = new PromoProductJoinResponseDto();
        PromoProductJoinResponseDto dto2 = new PromoProductJoinResponseDto();
        dto1.setPromoCode("PROMO1");
        dto1.setProductId(123L);
        dto2.setPromoCode("PROMO1");
        dto2.setProductId(123L);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
