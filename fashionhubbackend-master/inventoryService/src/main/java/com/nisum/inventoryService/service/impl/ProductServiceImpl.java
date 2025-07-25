package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dto.ProductAttributeDto;
import com.nisum.inventoryService.dto.ProductDto;
import com.nisum.inventoryService.dto.ProductFlatResponseDTO;
import com.nisum.inventoryService.dto.ProductResponseDto;
import com.nisum.inventoryService.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponseDto getAllProducts() {
        String url = "http://localhost:8000/products";

        try {
            ProductResponseDto response = restTemplate.getForObject(url, ProductResponseDto.class);
            return response != null ? response : new ProductResponseDto();
        } catch (Exception ex){
            return new ProductResponseDto();
        }
    }

    @Override
    public List<ProductFlatResponseDTO> getFlatProductList() {
        ProductResponseDto responseDTO = getAllProducts(); // Get the full response
        List<ProductFlatResponseDTO> flatList = new ArrayList<>();

        if (responseDTO.getProducts() != null) {
            for (ProductDto product : responseDTO.getProducts()) {
                String status = product.getStatus();
                int categoryId = product.getCategoryId();

                for (ProductAttributeDto attr : product.getAttributes()) {
                    ProductFlatResponseDTO flatDTO = new ProductFlatResponseDTO(
                            status,
                            attr.getSku(),
                            categoryId
                    );
                    flatList.add(flatDTO);
                }
            }
        }

        return flatList;
    }
}
