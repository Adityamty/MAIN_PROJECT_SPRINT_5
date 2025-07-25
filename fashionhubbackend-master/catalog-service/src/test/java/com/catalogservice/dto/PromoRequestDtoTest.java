package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoRequestDtoTest {
    @Test
    void testSettersAndGetters() {
        PromoRequestDto dto = new PromoRequestDto();
        dto.setPromoCode("PROMO1");
        dto.setDescription("desc");
        dto.setAmount(100);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals("desc", dto.getDescription());
        assertEquals(100, dto.getAmount());
    }
}
