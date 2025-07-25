package com.nisum.productmanagement.dto;

public class ProductAttributeDto {
    private String sku;
    private Double price;
    private String size;
    private String productImage;

    public ProductAttributeDto() {}

    // Constructor without SKU for frontend requests (SKU will be auto-generated)
    public ProductAttributeDto(Double price, String size, String productImage) {
        this.price = price;
        this.size = size;
        this.productImage = productImage;
    }

    // Constructor with SKU for complete object creation
    public ProductAttributeDto(String sku, Double price, String size, String productImage) {
        this.sku = sku;
        this.price = price;
        this.size = size;
        this.productImage = productImage;
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }
}
