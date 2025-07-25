package com.catalogservice.service.impl;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
import com.catalogservice.mapper.PromoProductJoinMapper;
import com.catalogservice.model.PromoProductJoin;
import com.catalogservice.repository.PromoProductJoinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromoProductJoinServiceImplTest {
    @Mock
    private PromoProductJoinRepository repository;
    @Mock
    private PromoProductJoinMapper mapper;
    @InjectMocks
    private PromoProductJoinServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        PromoProductJoinRequestDto request = new PromoProductJoinRequestDto();
        PromoProductJoin entity = new PromoProductJoin();
        PromoProductJoinResponseDto response = new PromoProductJoinResponseDto();
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);
        PromoProductJoinResponseDto result = service.save(request);
        assertEquals(response, result);
    }

    @Test
    void testGetAll() {
        List<PromoProductJoin> entities = Arrays.asList(new PromoProductJoin());
        List<PromoProductJoinResponseDto> dtos = Arrays.asList(new PromoProductJoinResponseDto());
        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(any(PromoProductJoin.class))).thenReturn(dtos.get(0));
        List<PromoProductJoinResponseDto> result = service.getAll();
        assertEquals(dtos.size(), result.size());
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);
        service.delete(id);
        verify(repository).deleteById(id);
    }
}
