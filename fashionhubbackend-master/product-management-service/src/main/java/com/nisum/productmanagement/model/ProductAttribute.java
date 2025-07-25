package com.nisum.productmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_attributes")
public class ProductAttribute {
    @Id
    @Column(nullable = false, unique = true)
    private String sku;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String size;

    @Column(name = "product_image")
    private String productImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Product product;

    // Constructors
    public ProductAttribute() {}

    public ProductAttribute(String sku, Long productId, Double price, String size, String productImage) {
        this.sku = sku;
        this.productId = productId;
        this.price = price;
        this.size = size;
        this.productImage = productImage;
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
