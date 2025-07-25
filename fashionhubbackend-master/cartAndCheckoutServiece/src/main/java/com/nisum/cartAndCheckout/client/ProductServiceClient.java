package com.nisum.cartAndCheckout.client;

import com.nisum.cartAndCheckout.dto.response.AvailableQuantityDto;
import com.nisum.cartAndCheckout.dto.response.ProductAttributesDto;
import com.nisum.cartAndCheckout.dto.response.ProductCategoryDto;
import com.nisum.cartAndCheckout.dto.response.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//@RequiredArgsConstructor
//public class ProductServiceClient {
//
//    @Autowired
//    private final RestTemplate restTemplate;
//
//    public ProductDto getProductByProductId(int productId) {
//        return restTemplate.getForObject("http://PRODUCT-SERVICE/api/products?productId={productId}",
//                ProductDto.class, productId);
//    }
//
//    public ProductAttributesDto getAttributesBySku(int productId,String size) {
//        return restTemplate.getForObject("http://PRODUCT-SERVICE/api/attributes?productId={productId}&size={size}",
//                ProductAttributesDto.class, productId,size);
//    }
//
//    public List<ProductAttributesDto> getAllAttributesByProductId(int productId) {
//        ProductAttributesDto[] arr = restTemplate.getForObject(
//                "http://PRODUCT-SERVICE/api/attributes/product/{id}",
//                ProductAttributesDto[].class, productId);
//        return arr != null ? Arrays.asList(arr) : null;
//    }
//
//    public ProductCategoryDto getCategoryBySku(String sku) {
//        return restTemplate.getForObject("http://PRODUCT-SERVICE/api/category?sku={sku}",
//                ProductCategoryDto.class, sku);
//    }
//
//    public Integer getStockQuantityBySku(String sku) {
//        return restTemplate.getForObject("http://INVENTORY-SERVICE/api/stock?sku={sku}",
//                Integer.class, sku);
//    }
//}
@Service
@RequiredArgsConstructor
public class ProductServiceClient {

    // Comment this out or leave it, but don't call it while mocking
    // private final RestTemplate restTemplate;

    public ProductDto getProductByProductId(int productId) {
        // Mock response
        return new ProductDto(
                productId,
                "SKU123",
                "Mock Product Name",
                101,
                "ACTIVE",
                java.time.LocalDateTime.now(),
                1001
        );
    }

    public ProductAttributesDto getAttributesByProductIdAndSize(int productId, String size) {
        return new ProductAttributesDto(
                "SKU003",
                productId,
                size,
                new java.math.BigDecimal("150.0"),
                "https://mockurl.com/image.png"
        );
    }

    public List<ProductAttributesDto> getAllAttributesByProductId(int productId) {
        return List.of(
                new ProductAttributesDto("MOCKSKU123", productId, "S", new java.math.BigDecimal("120.0"), "https://mockurl.com/image_s.png"),
                new ProductAttributesDto("MOCKSKU123", productId, "M", new java.math.BigDecimal("120.0"), "https://mockurl.com/image_m.png")
        );
    }

    public ProductCategoryDto getCategoryBySku(String sku) {
        ProductCategoryDto dto = new ProductCategoryDto();
        dto.setSku(sku);
        dto.setDiscount(10); // 10.5% discount
        return dto;
    }

    public AvailableQuantityDto getStockQuantityBySku(String sku) {
        AvailableQuantityDto dto=new AvailableQuantityDto();
        dto.setSku(sku);
        dto.setAvailableQuantity(5);
        return dto;
    }
}