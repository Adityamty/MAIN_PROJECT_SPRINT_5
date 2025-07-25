package com.nisum.inventoryService.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sku", "categoryId"}) // optional but matches DB constraint
})
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private String location;
    private int availableQty;
    private int categoryId;

    public Inventory() {
    }

    public Inventory(String sku, String location, int availableQty, int categoryId) {
        this.sku = sku;
        this.location = location;
        this.availableQty = availableQty;
        this.categoryId = categoryId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getAvailableQty() { return availableQty; }
    public void setAvailableQty(int availableQty) { this.availableQty = availableQty; }

    public int getCategoryId() { return categoryId; } // ✅ ADDED
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; } // ✅ ADDED
}
