package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoProductJoinRequestDtoTest {
    @Test
    void testSettersAndGetters() {
        PromoProductJoinRequestDto dto = new PromoProductJoinRequestDto();
        dto.setPromoCode("PROMO1");
        dto.setProductId(123L);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals(123L, dto.getProductId());
    }
}
