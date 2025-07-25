package com.nisum.inventoryService.dto;

import java.util.List;

public class ProductResponseDto {
    private List<ProductDto> products;
    private int totalProductCount;
    private int recentlyUpdatedProductCount;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private int totalResults;

    // Getters and Setters
    public List<ProductDto> getProducts() {
        return products;
    }
    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public int getTotalProductCount() {
        return totalProductCount;
    }
    public void setTotalProductCount(int totalProductCount) {
        this.totalProductCount = totalProductCount;
    }

    public int getRecentlyUpdatedProductCount() {
        return recentlyUpdatedProductCount;
    }
    public void setRecentlyUpdatedProductCount(int recentlyUpdatedProductCount) {
        this.recentlyUpdatedProductCount = recentlyUpdatedProductCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}

