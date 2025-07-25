package com.nisum.productmanagement.dto;

public class ProductAttributeDetailDto {
    private String sku;
    private Long productId;
    private String productName;
    private String categoryName;
    private Long categoryId;
    private String status;
    private Long sellerId;
    private Double price;
    private String size;
    private String productImage;

    public ProductAttributeDetailDto() {}

    public ProductAttributeDetailDto(String sku, Long productId, String productName, 
                                   String categoryName, Long categoryId, String status, 
                                   Long sellerId, Double price, String size, String productImage) {
        this.sku = sku;
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.status = status;
        this.sellerId = sellerId;
        this.price = price;
        this.size = size;
        this.productImage = productImage;
    }

    // Getters and setters
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }
}
