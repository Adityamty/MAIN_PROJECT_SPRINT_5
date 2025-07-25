package com.nisum.productmanagement.controller;

import com.nisum.productmanagement.dto.PaginationResult;
import com.nisum.productmanagement.dto.ProductAttributeDetailDto;
import com.nisum.productmanagement.dto.ProductDto;
import com.nisum.productmanagement.dto.SellerDto;
import com.nisum.productmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // This endpoint retrieves all products with pagination and filtering options
    @GetMapping
    public ResponseEntity<PaginationResult<ProductDto>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sortBy", defaultValue = "lastModifiedDate") String sortBy,
            @RequestParam(value="categoryId", required = false) Long categoryId,
            @RequestParam(value="minPrice", required = false) Double minPrice,
            @RequestParam(value="maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sort", defaultValue = "desc") String sort) {
        
        try {
            // Validate pagination parameters
            if (page < 1) page = 1;
            if (size < 1 || size > 100) size = 10; // Limit max page size to prevent performance issues
            
            PaginationResult<ProductDto> result = productService.getAllProducts(
                page, size, status, search, sortBy, categoryId, minPrice, maxPrice, sort);
            
            return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.getTotalProductCount()))
                .header("X-Page", String.valueOf(page))
                .header("X-Page-Size", String.valueOf(size))
                .body(result);
        } catch (Exception e) {
            System.err.println("Error fetching products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // This endpoint retrieves the total count of products
    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getAllProductIds() {
        return ResponseEntity.ok(productService.getAllProductIds());
    }

    // This endpoint retrieves a product by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // This endpoint retrieves all sizes for a specific product
    @GetMapping("/{id}/sizes")
    public ResponseEntity<List<String>> getProductSizes(@PathVariable Long id) {
        List<String> sizes = productService.getProductSizes(id);
        return ResponseEntity.ok(sizes);
    }

    // This endpoint retrieves all SKUs for a specific product, optionally filtered by size
    @GetMapping("/{id}/skus")
    public ResponseEntity<List<String>> getProductSkus(@PathVariable Long id,
                                                       @RequestParam(value = "size", required = false) String size) {
        List<String> skus = productService.getProductSkus(id, size);
        return ResponseEntity.ok(skus);
    }

    // This endpoint retrieves all sellers for a specific product
    @GetMapping("/{id}/sellers")
    public ResponseEntity<SellerDto> getProductSeller(@PathVariable Long id) {
        SellerDto seller = productService.getProductSellerDetails(id);
        return ResponseEntity.ok(seller);
    }

    // This endpoint creates a new product
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // This endpoint updates an existing product by its ID
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    // This endpoint deletes a product by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);

        Map<String, String> response = new HashMap<>();
        if (deleted) {
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Product not found");
            return ResponseEntity.notFound().build();
        }
    }

    // This endpoint retrieves recently modified products
    @PostMapping("/import")
    public ResponseEntity<Map<String, String>> importProducts(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            productService.importProducts(file);
            response.put("message", "Products imported successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Import failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // This endpoint retrieves all product attributes with pagination and search
    @GetMapping("/attributes")
    public ResponseEntity<PaginationResult<ProductAttributeDetailDto>> getAllProductAttributes(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sortBy", defaultValue = "productName") String sortBy,
            @RequestParam(value = "sort", defaultValue = "asc") String sort,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        PaginationResult<ProductAttributeDetailDto> result = productService.getAllProductAttributes(page, size, search, sortBy, sort, status, minPrice, maxPrice);
        return ResponseEntity.ok(result);
    }

    // This endpoint retrieves product statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getProductStats() {
        Map<String, Object> stats = productService.getProductStats();
        return ResponseEntity.ok(stats);
    }

    // This endpoint retrieves products by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        try {
            List<ProductDto> products = productService.getProductsByCategory(categoryId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
