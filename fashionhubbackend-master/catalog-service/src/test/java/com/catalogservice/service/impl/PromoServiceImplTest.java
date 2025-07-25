package com.catalogservice.service.impl;

import com.catalogservice.dto.PromoRequestDto;
import com.catalogservice.dto.PromoResponseDto;
import com.catalogservice.mapper.PromoMapper;
import com.catalogservice.model.Promo;
import com.catalogservice.repository.PromoProductJoinRepository;
import com.catalogservice.repository.PromoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromoServiceImplTest {
    @Mock
    private PromoRepository repository;
    @Mock
    private PromoMapper mapper;
    @Mock
    private PromoProductJoinRepository promoProductJoinRepo;
    @InjectMocks
    private PromoServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        PromoRequestDto request = new PromoRequestDto();
        Promo entity = new Promo();
        PromoResponseDto response = new PromoResponseDto();
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);
        PromoResponseDto result = service.save(request);
        assertEquals(response, result);
    }

    @Test
    void testGetAll() {
        List<Promo> entities = Arrays.asList(new Promo());
        List<PromoResponseDto> dtos = Arrays.asList(new PromoResponseDto());
        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(any(Promo.class))).thenReturn(dtos.get(0));
        List<PromoResponseDto> result = service.getAll();
        assertEquals(dtos.size(), result.size());
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        PromoRequestDto request = new PromoRequestDto();
        Promo entity = new Promo();
        PromoResponseDto response = new PromoResponseDto();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);
        PromoResponseDto result = service.update(id, request);
        assertEquals(response, result);
    }

    @Test
    void testUpdate_NotFound() {
        Long id = 99L;
        PromoRequestDto request = new PromoRequestDto();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.update(id, request));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);
        service.delete(id);
        verify(repository).deleteById(id);
    }

    @Test
    void testUpdateByPromoCode() {
        String promoCode = "PROMO1";
        PromoRequestDto request = new PromoRequestDto();
        Promo entity = new Promo();
        PromoResponseDto response = new PromoResponseDto();
        when(repository.findByPromoCode(promoCode)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);
        PromoResponseDto result = service.updateByPromoCode(promoCode, request);
        assertEquals(response, result);
    }

    @Test
    void testUpdateByPromoCode_NotFound() {
        String promoCode = "NOT_FOUND";
        PromoRequestDto request = new PromoRequestDto();
        when(repository.findByPromoCode(promoCode)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateByPromoCode(promoCode, request));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testDeleteByPromoCode() {
        String promoCode = "PROMO1";
        doNothing().when(repository).deleteByPromoCode(promoCode);
        service.deleteByPromoCode(promoCode);
        verify(repository).deleteByPromoCode(promoCode);
    }

    @Test
    void testGetPromosByProductIds() {
        List<Integer> productIds = Arrays.asList(1, 2);
        List<Long> productIdsLong = Arrays.asList(1L, 2L);
        com.catalogservice.model.PromoProductJoin join1 = new com.catalogservice.model.PromoProductJoin();
        join1.setPromoCode("PROMO1");
        com.catalogservice.model.PromoProductJoin join2 = new com.catalogservice.model.PromoProductJoin();
        join2.setPromoCode("PROMO2");
        List<com.catalogservice.model.PromoProductJoin> joins = Arrays.asList(join1, join2);
        List<String> promoCodes = Arrays.asList("PROMO1", "PROMO2");
        Promo promo1 = new Promo();
        Promo promo2 = new Promo();
        List<Promo> promos = Arrays.asList(promo1, promo2);
        PromoResponseDto dto1 = new PromoResponseDto();
        PromoResponseDto dto2 = new PromoResponseDto();
        when(promoProductJoinRepo.findByProductIdIn(productIdsLong)).thenReturn(joins);
        when(repository.findByPromoCodeIn(promoCodes)).thenReturn(promos);
        when(mapper.toDto(promo1)).thenReturn(dto1);
        when(mapper.toDto(promo2)).thenReturn(dto2);
        List<PromoResponseDto> result = service.getPromosByProductIds(productIds);
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }
}
