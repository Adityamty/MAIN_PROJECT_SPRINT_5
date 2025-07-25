package com.nisum.productmanagement.service;

import com.nisum.productmanagement.dto.SellerDto;
import com.nisum.productmanagement.model.Seller;
import com.nisum.productmanagement.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerService sellerService;

    private Seller testSeller1;
    private Seller testSeller2;

    @BeforeEach
    void setUp() {
        testSeller1 = new Seller();
        testSeller1.setId(1L);
        testSeller1.setSellerName("Electronics Store");
        testSeller1.setContactName("John Doe");
        testSeller1.setEmail("john@electronics.com");
        testSeller1.setPhoneNumber("123-456-7890");
        testSeller1.setAddressLine1("123 Main St");
        testSeller1.setAddressLine2("Suite 100");
        testSeller1.setCity("New York");
        testSeller1.setState("NY");
        testSeller1.setZipCode("10001");
        testSeller1.setCountry("USA");

        testSeller2 = new Seller();
        testSeller2.setId(2L);
        testSeller2.setSellerName("Book World");
        testSeller2.setContactName("Jane Smith");
        testSeller2.setEmail("jane@bookworld.com");
        testSeller2.setPhoneNumber("987-654-3210");
        testSeller2.setAddressLine1("456 Oak Ave");
        testSeller2.setAddressLine2("Floor 2");
        testSeller2.setCity("Los Angeles");
        testSeller2.setState("CA");
        testSeller2.setZipCode("90210");
        testSeller2.setCountry("USA");
    }

    @Test
    void getAllSellers_WhenSellersExist_ReturnsSellerDtoList() {
        // Arrange
        List<Seller> sellers = Arrays.asList(testSeller1, testSeller2);
        when(sellerRepository.findAll()).thenReturn(sellers);

        // Act
        List<SellerDto> result = sellerService.getAllSellers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verify first seller
        SellerDto dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("Electronics Store", dto1.getSellerName());
        assertEquals("John Doe", dto1.getContactName());
        assertEquals("john@electronics.com", dto1.getEmail());
        assertEquals("123-456-7890", dto1.getPhoneNumber());
        assertEquals("123 Main St", dto1.getAddressLine1());
        assertEquals("Suite 100", dto1.getAddressLine2());
        assertEquals("New York", dto1.getCity());
        assertEquals("NY", dto1.getState());
        assertEquals("10001", dto1.getZipCode());
        assertEquals("USA", dto1.getCountry());
        
        // Verify second seller
        SellerDto dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("Book World", dto2.getSellerName());
        assertEquals("Jane Smith", dto2.getContactName());
        assertEquals("jane@bookworld.com", dto2.getEmail());
        assertEquals("987-654-3210", dto2.getPhoneNumber());
        assertEquals("456 Oak Ave", dto2.getAddressLine1());
        assertEquals("Floor 2", dto2.getAddressLine2());
        assertEquals("Los Angeles", dto2.getCity());
        assertEquals("CA", dto2.getState());
        assertEquals("90210", dto2.getZipCode());
        assertEquals("USA", dto2.getCountry());

        verify(sellerRepository, times(1)).findAll();
    }

    @Test
    void getAllSellers_WhenNoSellersExist_ReturnsEmptyList() {
        // Arrange
        when(sellerRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<SellerDto> result = sellerService.getAllSellers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(sellerRepository, times(1)).findAll();
    }

    @Test
    void getAllSellers_WhenSellersHaveNullValues_HandlesGracefully() {
        // Arrange
        Seller sellerWithNulls = new Seller();
        sellerWithNulls.setId(3L);
        sellerWithNulls.setSellerName("Minimal Seller");
        // Other fields remain null

        when(sellerRepository.findAll()).thenReturn(Arrays.asList(sellerWithNulls));

        // Act
        List<SellerDto> result = sellerService.getAllSellers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        
        SellerDto dto = result.get(0);
        assertEquals(3L, dto.getId());
        assertEquals("Minimal Seller", dto.getSellerName());
        assertNull(dto.getContactName());
        assertNull(dto.getEmail());
        assertNull(dto.getPhoneNumber());
        assertNull(dto.getAddressLine1());
        assertNull(dto.getAddressLine2());
        assertNull(dto.getCity());
        assertNull(dto.getState());
        assertNull(dto.getZipCode());
        assertNull(dto.getCountry());

        verify(sellerRepository, times(1)).findAll();
    }

    @Test
    void getAllSellers_WhenRepositoryThrowsException_PropagatesException() {
        // Arrange
        when(sellerRepository.findAll()).thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> sellerService.getAllSellers());
        
        assertEquals("Database connection failed", exception.getMessage());
        verify(sellerRepository, times(1)).findAll();
    }
}