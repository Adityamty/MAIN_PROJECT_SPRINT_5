package com.catalogservice.controller;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.service.ProductCategoryService;
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

class ProductCategoryControllerTest {
    @Mock
    private ProductCategoryService productCategoryService;

    @InjectMocks
    private ProductCategoryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(productCategoryService.save(request)).thenReturn(response);
        ResponseEntity<ProductCategoryResponseDto> result = controller.save(request);
        assertEquals(response, result.getBody());
        verify(productCategoryService).save(request);
    }

    @Test
    void testGetAll() {
        List<ProductCategoryResponseDto> list = Arrays.asList(new ProductCategoryResponseDto());
        when(productCategoryService.getAll()).thenReturn(list);
        ResponseEntity<List<ProductCategoryResponseDto>> result = controller.getAll();
        assertEquals(list, result.getBody());
        verify(productCategoryService).getAll();
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(productCategoryService.update(id, request)).thenReturn(response);
        ResponseEntity<ProductCategoryResponseDto> result = controller.update(id, request);
        assertEquals(response, result.getBody());
        verify(productCategoryService).update(id, request);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(productCategoryService).delete(id);
        ResponseEntity<Void> result = controller.delete(id);
        assertEquals(204, result.getStatusCodeValue());
        verify(productCategoryService).delete(id);
    }

    @Test
    void testUpdateDiscount() {
        Long categoryId = 2L;
        Double discount = 10.0;
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(productCategoryService.updateDiscountByCategoryId(categoryId, discount)).thenReturn(response);
        ResponseEntity<ProductCategoryResponseDto> result = controller.updateDiscount(categoryId, discount);
        assertEquals(response, result.getBody());
        verify(productCategoryService).updateDiscountByCategoryId(categoryId, discount);
    }

    @Test
    void testGetDiscountBySku_Found() {
        String sku = "SKU123";
        Double discount = 10.0;
        com.catalogservice.dto.ProductCategoryDiscountResponseDto response = new com.catalogservice.dto.ProductCategoryDiscountResponseDto(sku, discount);
        when(productCategoryService.getDiscountBySku(sku)).thenReturn(response);
        ResponseEntity<com.catalogservice.dto.ProductCategoryDiscountResponseDto> result = controller.getDiscountBySku(sku);
        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
        verify(productCategoryService).getDiscountBySku(sku);
    }

    @Test
    void testGetDiscountBySku_NotFound() {
        String sku = "SKU_NOT_FOUND";
        when(productCategoryService.getDiscountBySku(sku)).thenReturn(null);
        ResponseEntity<com.catalogservice.dto.ProductCategoryDiscountResponseDto> result = controller.getDiscountBySku(sku);
        assertNull(result.getBody());
        assertEquals(404, result.getStatusCodeValue());
        verify(productCategoryService).getDiscountBySku(sku);
    }
}
