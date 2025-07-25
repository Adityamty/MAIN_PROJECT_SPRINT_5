package com.nisum.cartAndCheckout.service;

import com.nisum.cartAndCheckout.dto.PromoDTO;
import com.nisum.cartAndCheckout.dto.response.PromoResponse;
import com.nisum.cartAndCheckout.exception.PromoServiceUnavailableException;
import com.nisum.cartAndCheckout.mapper.PromoMapper;
import com.nisum.cartAndCheckout.service.implementation.PromoServiceImpl;
import com.nisum.cartAndCheckout.validation.PromoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PromoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PromoServiceImpl promoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPromosForProducts_Success() {
        List<Integer> productIds = List.of(101, 102);
        List<PromoResponse> promoResponses = List.of(
                new PromoResponse("SAVE10", "DISCOUNT", "10% off", 10),
                new PromoResponse("FLAT50", "FLAT", "Flat Rs. 50 off", 50)
        );

        ResponseEntity<List<PromoResponse>> mockResponse = new ResponseEntity<>(promoResponses, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
            .thenReturn(mockResponse);

        List<PromoDTO> promoDTOList = promoService.getPromosForProducts(productIds);

        assertEquals(2, promoDTOList.size());
        assertEquals("SAVE10", promoDTOList.get(0).getPromoCode());
        assertEquals("FLAT50", promoDTOList.get(1).getPromoCode());
    }

    @Test
    void testGetPromosForProducts_EmptyList() {
        List<Integer> productIds = Collections.emptyList();

        PromoServiceUnavailableException exception = assertThrows(
                PromoServiceUnavailableException.class,
                () -> promoService.getPromosForProducts(productIds)
        );

        assertEquals("Promo fetch failed.", exception.getMessage());
    }

    @Test
    void testGetPromosForProducts_NullProductIds() {
        PromoServiceUnavailableException exception = assertThrows(
                PromoServiceUnavailableException.class,
                () -> promoService.getPromosForProducts(null)
        );

        assertEquals("Promo fetch failed.", exception.getMessage());
    }

    @Test
    void testGetPromosForProducts_NullResponse() {
        List<Integer> productIds = List.of(101);

        ResponseEntity<List<PromoResponse>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
            .thenReturn(responseEntity);

        assertThrows(PromoServiceUnavailableException.class, () ->
                promoService.getPromosForProducts(productIds));
    }

    @Test
    void testGetPromosForProducts_ThrowsException() {
        List<Integer> productIds = List.of(101);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
            .thenThrow(new RuntimeException("Server down"));

        PromoServiceUnavailableException exception = assertThrows(
                PromoServiceUnavailableException.class,
                () -> promoService.getPromosForProducts(productIds));

        assertTrue(exception.getMessage().contains("Promo fetch failed"));
    }
}
