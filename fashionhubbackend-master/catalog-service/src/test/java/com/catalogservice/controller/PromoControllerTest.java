package com.catalogservice.controller;

import com.catalogservice.dto.PromoRequestDto;
import com.catalogservice.dto.PromoResponseDto;
import com.catalogservice.service.PromoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromoControllerTest {
    @Mock
    private PromoService promoService;

    @InjectMocks
    private PromoController controller;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
            // resources will be closed automatically
        }
    }

    @Test
    void testSave() {
        PromoRequestDto request = new PromoRequestDto();
        PromoResponseDto response = new PromoResponseDto();
        when(promoService.save(request)).thenReturn(response);
        ResponseEntity<PromoResponseDto> result = controller.save(request);
        assertEquals(response, result.getBody());
        verify(promoService).save(request);
    }

    @Test
    void testGetAll() {
        List<PromoResponseDto> list = List.of(new PromoResponseDto());
        when(promoService.getAll()).thenReturn(list);
        ResponseEntity<List<PromoResponseDto>> result = controller.getAll();
        assertEquals(list, result.getBody());
        verify(promoService).getAll();
    }

    @Test
    void testUpdateByPromocode() {
        String promocode = "PROMO123";
        PromoRequestDto request = new PromoRequestDto();
        PromoResponseDto response = new PromoResponseDto();
        when(promoService.updateByPromoCode(promocode, request)).thenReturn(response);
        ResponseEntity<PromoResponseDto> result = controller.updateByPromoCode(promocode, request);
        assertEquals(response, result.getBody());
        verify(promoService).updateByPromoCode(promocode, request);
    }

    @Test
    void testDeleteByPromocode() {
        String promocode = "PROMO123";
        doNothing().when(promoService).deleteByPromoCode(promocode);
        ResponseEntity<Void> result = controller.deleteByPromoCode(promocode);
        assertEquals(204, result.getStatusCode().value());
        verify(promoService).deleteByPromoCode(promocode);
    }

    @Test
    void testGetPromosByProductIds() {
        List<Integer> productIds = Arrays.asList(1, 2);
        List<PromoResponseDto> responseList = Arrays.asList(new PromoResponseDto(), new PromoResponseDto());
        when(promoService.getPromosByProductIds(productIds)).thenReturn(responseList);
        ResponseEntity<List<PromoResponseDto>> result = controller.getPromosByProductIds(productIds);
        assertEquals(responseList, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
        verify(promoService).getPromosByProductIds(productIds);
    }

    @Test
    void testGetPromosByProductId() {
        Integer productId = 1;
        List<PromoResponseDto> promos = Arrays.asList(new PromoResponseDto(), new PromoResponseDto());
        when(promoService.getPromosByProductIds(List.of(productId))).thenReturn(promos);
        ResponseEntity<List<PromoResponseDto>> result = controller.getPromosByProductId(productId);
        assertEquals(promos, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
        verify(promoService).getPromosByProductIds(List.of(productId));
        // Ensure discount is set to null for each promo
        result.getBody().forEach(promo -> assertNull(promo.getDiscount()));
    }
}
