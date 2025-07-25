package com.nisum.productmanagement.service;

import com.nisum.productmanagement.dto.*;
import com.nisum.productmanagement.exception.ProductNotFoundException;
import com.nisum.productmanagement.exception.CategoryNotFoundException;
import com.nisum.productmanagement.model.Product;
import com.nisum.productmanagement.model.ProductAttribute;
import com.nisum.productmanagement.model.Category;
import com.nisum.productmanagement.model.Seller;
import com.nisum.productmanagement.repository.ProductRepository;
import com.nisum.productmanagement.repository.CategoryRepository;
import com.nisum.productmanagement.repository.SellerRepository;
import com.nisum.productmanagement.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final CsvParserService csvParserService;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, 
                          CategoryRepository categoryRepository,
                          CategoryService categoryService,
                          CsvParserService csvParserService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.csvParserService = csvParserService;
    }

    public int getTotalProductCount() {
        return (int) productRepository.count();
    }

    // Simple getAllProducts method for backward compatibility
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Full getAllProducts method with pagination and filtering
    public PaginationResult<ProductDto> getAllProducts(
            int page,
            int size,
            String status,
            String search,
            String sortBy,
            Long categoryId,
            Double minPrice,
            Double maxPrice,
            String sort) {

        String sortField = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "lastModifiedDate";
        Sort.Direction direction = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortField));

        Specification<Product> spec = Specification.where(null);

        if (search != null && !search.trim().isEmpty()) {
            spec = spec.and(ProductSpecification.searchProducts(search));
        }
        if (status != null && !status.trim().isEmpty()) {
            spec = spec.and(ProductSpecification.hasStatus(status));
        }
        if (categoryId != null) {
            spec = spec.and(ProductSpecification.hasCategoryId(categoryId));
        }
        if (minPrice != null && maxPrice != null) {
            spec = spec.and(ProductSpecification.priceBetween(minPrice, maxPrice));
        } else if (minPrice != null) {
            spec = spec.and(ProductSpecification.priceGreaterThanOrEqual(minPrice));
        } else if (maxPrice != null) {
            spec = spec.and(ProductSpecification.priceBetween(null, maxPrice));
        }

        Page<Product> productPage = productRepository.findAll(spec, pageable);

        LocalDateTime threshold = LocalDateTime.now().minusDays(1);
        int recentlyUpdatedCount = productRepository.countRecentlyModifiedProducts(threshold);

        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new PaginationResult<>(
                productDtos,
                getTotalProductCount(),
                recentlyUpdatedCount,
                page,
                size,
                (int) productPage.getTotalElements()
        );
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return convertToDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        validateProductDto(productDto);
        validateCategory(productDto.getCategoryId());

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategoryId(productDto.getCategoryId());
        product.setStatus(productDto.getStatus() != null ? productDto.getStatus() : "Active");
        product.setSellerId(productDto.getSellerId());
        product.setLastModifiedDate(LocalDateTime.now());

        // Save the product first to get the generated ID
        Product savedProduct = productRepository.save(product);

        // Now create and set attributes with auto-generated SKUs
        if (productDto.getAttributes() != null) {
            final Product finalProduct = savedProduct;
            List<ProductAttribute> attributes = productDto.getAttributes().stream()
                    .map(attr -> {
                        int attributeIndex = productDto.getAttributes().indexOf(attr) + 1;
                        String autoGeneratedSku = "SKU-" + finalProduct.getId() + attributeIndex;
                        
                        return new ProductAttribute(
                                autoGeneratedSku,
                                finalProduct.getId(),
                                attr.getPrice(),
                                attr.getSize(),
                                attr.getProductImage()
                        );
                    })
                    .collect(Collectors.toList());

            attributes.forEach(attr -> attr.setProduct(finalProduct));
            savedProduct.setAttributes(attributes);
            savedProduct = productRepository.save(savedProduct);
        }

        return convertToDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        validateProductDto(productDto);

        if (!existingProduct.getCategoryId().equals(productDto.getCategoryId())) {
            validateCategory(productDto.getCategoryId());
        }

        existingProduct.setName(productDto.getName());
        existingProduct.setCategoryId(productDto.getCategoryId());
        existingProduct.setStatus(productDto.getStatus());
        existingProduct.setSellerId(productDto.getSellerId());

        // Handle attributes update
        if (productDto.getAttributes() != null) {
            existingProduct.getAttributes().clear();

            for (int i = 0; i < productDto.getAttributes().size(); i++) {
                ProductAttributeDto attrDto = productDto.getAttributes().get(i);
                
                ProductAttribute attribute = new ProductAttribute();
                
                if (attrDto.getSku() != null && !attrDto.getSku().trim().isEmpty()) {
                    attribute.setSku(attrDto.getSku().trim());
                } else {
                    attribute.setSku("SKU-" + id + (i + 1));
                }
                
                attribute.setPrice(attrDto.getPrice());
                attribute.setSize(attrDto.getSize().toUpperCase());
                attribute.setProductImage(attrDto.getProductImage());
                attribute.setProductId(existingProduct.getId());
                attribute.setProduct(existingProduct);
                
                existingProduct.getAttributes().add(attribute);
            }
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        return true;
    }

    public void importProducts(MultipartFile file) {
        List<Product> products = csvParserService.parseCsv(file);

        for (Product product : products) {
            Optional<Category> category = categoryService.getCategoryByName(product.getCategoryName());
            if (!category.isPresent()) {
                throw new CategoryNotFoundException("Category not found: " + product.getCategoryName());
            }

            product.setCategoryId((long) category.get().getId());
            product.setStatus(product.getStatus().toUpperCase());

            if (product.getSellerId() == null) {
                throw new IllegalArgumentException("Seller ID is required for import");
            }
        }

        productRepository.saveAll(products);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategoryId(product.getCategoryId());
        dto.setStatus(product.getStatus());
        dto.setSellerId(product.getSellerId());
        dto.setLastModifiedDate(product.getLastModifiedDate());
        
        try {
            Optional<Category> category = categoryRepository.findById(product.getCategoryId().intValue());
            dto.setCategoryName(category.isPresent() ? category.get().getCategoryName() : "Unknown");
        } catch (Exception e) {
            System.err.println("Failed to fetch category for ID " + product.getCategoryId() + ": " + e.getMessage());
            dto.setCategoryName("Unknown");
        }
        
        if (product.getAttributes() != null && !product.getAttributes().isEmpty()) {
            List<ProductAttributeDto> attributeDtos = product.getAttributes().stream()
                .map(attr -> new ProductAttributeDto(
                    attr.getSku(),
                    attr.getPrice(),
                    attr.getSize(),
                    attr.getProductImage()
                ))
                .collect(Collectors.toList());
            dto.setAttributes(attributeDtos);
        } else {
            dto.setAttributes(new ArrayList<>());
        }
        
        return dto;
    }

    private void validateProductDto(ProductDto productDto) {
        if (productDto.getName() == null || productDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (productDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID is required");
        }
        if (productDto.getSellerId() == null) {
            throw new IllegalArgumentException("Seller ID is required");
        }
    }

    private void validateCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId.intValue());
        if (!category.isPresent()) {
            throw new CategoryNotFoundException("Category not found with id: " + categoryId);
        }
    }

    public List<Long> getAllProductIds() {
        return productRepository.findAll().stream()
                .map(Product::getId)
                .collect(Collectors.toList());
    }

    public List<String> getProductSizes(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        String sizes = product.getAttributes().stream()
                .map(ProductAttribute::getSize)
                .filter(size -> size != null && !size.trim().isEmpty())
                .collect(Collectors.joining(","));

        if (sizes.isEmpty()) {
            throw new ProductNotFoundException("No sizes found for product with id: " + id);
        }
        return List.of(sizes.split(","));
    }

    public List<Long> getProductSellers(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return List.of(product.getSellerId());
    }

    public SellerDto getProductSellerDetails(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        Long sellerId = product.getSellerId();
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with id: " + sellerId));
        return new SellerDto(
                seller.getId(),
                seller.getSellerName(),
                seller.getContactName(),
                seller.getEmail(),
                seller.getPhoneNumber(),
                seller.getAddressLine1(),
                seller.getAddressLine2(),
                seller.getCity(),
                seller.getState(),
                seller.getZipCode(),
                seller.getCountry()
        );
    }

    public List<String> getProductSkus(Long id, String size) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (product.getAttributes() == null || product.getAttributes().isEmpty()) {
            return List.of();
        }

        return product.getAttributes().stream()
                .filter(attr -> size == null || size.trim().isEmpty() || size.equalsIgnoreCase(attr.getSize()))
                .map(ProductAttribute::getSku)
                .filter(sku -> sku != null && !sku.trim().isEmpty())
                .collect(Collectors.toList());
    }

    public PaginationResult<ProductAttributeDetailDto> getAllProductAttributes(
            int page,
            int size,
            String search,
            String sortBy,
            String sort,
            String status,
            Double minPrice,
            Double maxPrice) {

        Specification<Product> spec = Specification.where(null);

        if (search != null && !search.trim().isEmpty()) {
            spec = spec.and(ProductSpecification.searchProductsForAttributes(search));
        }
        if (status != null && !status.trim().isEmpty()) {
            spec = spec.and(ProductSpecification.hasStatus(status));
        }
        if (minPrice != null && maxPrice != null) {
            spec = spec.and(ProductSpecification.priceBetween(minPrice, maxPrice));
        } else if (minPrice != null) {
            spec = spec.and(ProductSpecification.priceGreaterThanOrEqual(minPrice));
        }

        List<Product> allMatchingProducts = productRepository.findAll(spec);
        
        List<ProductAttributeDetailDto> allMatchingAttributes = allMatchingProducts.stream()
                .filter(product -> product.getAttributes() != null && !product.getAttributes().isEmpty())
                .flatMap(product -> {
                    try {
                        Optional<Category> category = categoryRepository.findById(product.getCategoryId().intValue());
                        String categoryName = category.isPresent() ? category.get().getCategoryName() : "Unknown";
                        
                        return product.getAttributes().stream()
                                .filter(attr -> {
                                    if (minPrice != null && attr.getPrice() < minPrice) return false;
                                    if (maxPrice != null && attr.getPrice() > maxPrice) return false;
                                    return true;
                                })
                                .map(attr -> new ProductAttributeDetailDto(
                                        attr.getSku(),
                                        product.getId(),
                                        product.getName(),
                                        categoryName,
                                        product.getCategoryId(),
                                        product.getStatus(),
                                        product.getSellerId(),
                                        attr.getPrice(),
                                        attr.getSize(),
                                        attr.getProductImage()
                                ));
                    } catch (Exception e) {
                        return product.getAttributes().stream()
                                .filter(attr -> {
                                    if (minPrice != null && attr.getPrice() < minPrice) return false;
                                    if (maxPrice != null && attr.getPrice() > maxPrice) return false;
                                    return true;
                                })
                                .map(attr -> new ProductAttributeDetailDto(
                                        attr.getSku(),
                                        product.getId(),
                                        product.getName(),
                                        "Unknown",
                                        product.getCategoryId(),
                                        product.getStatus(),
                                        product.getSellerId(),
                                        attr.getPrice(),
                                        attr.getSize(),
                                        attr.getProductImage()
                                ));
                    }
                })
                .collect(Collectors.toList());

        String sortField = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "productName";
        boolean ascending = "asc".equalsIgnoreCase(sort);
        
        allMatchingAttributes.sort((a, b) -> {
            int result = 0;
            switch (sortField) {
                case "productName":
                    result = a.getProductName().compareToIgnoreCase(b.getProductName());
                    break;
                case "sku":
                    result = a.getSku().compareToIgnoreCase(b.getSku());
                    break;
                case "price":
                    result = Double.compare(a.getPrice(), b.getPrice());
                    break;
                case "size":
                    result = a.getSize().compareToIgnoreCase(b.getSize());
                    break;
                default:
                    result = a.getProductName().compareToIgnoreCase(b.getProductName());
            }
            return ascending ? result : -result;
        });

        int totalElements = allMatchingAttributes.size();
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        
        List<ProductAttributeDetailDto> paginatedAttributes = allMatchingAttributes.subList(
            Math.max(0, startIndex), 
            Math.max(0, endIndex)
        );

        return new PaginationResult<>(
                paginatedAttributes,
                totalElements,
                0,
                page,
                size,
                totalElements
        );
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getProductStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", getTotalProductCount());
        
        LocalDateTime threshold = LocalDateTime.now().minusDays(1);
        int recentlyUpdated = productRepository.countRecentlyModifiedProducts(threshold);
        stats.put("recentlyUpdated", recentlyUpdated);
        
        return stats;
    }
}
