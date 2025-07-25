package com.nisum.productmanagement.dto;

import java.util.List;

public class PaginationResult<T> {
    private List<T> products;
    private int totalProductCount;
    private int recentlyUpdatedProductCount;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private int totalResults;

    public PaginationResult() {}

    public PaginationResult(List<T> products, int totalProductCount, int recentlyUpdatedProductCount,
                            int currentPage, int pageSize, int totalResults) {
        this.products = products;
        this.totalProductCount = totalProductCount;
        this.recentlyUpdatedProductCount = recentlyUpdatedProductCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) totalResults / pageSize);
        this.totalResults = totalResults;
    }

    // Getters and Setters
    public List<T> getProducts() { return products; }
    public void setProducts(List<T> products) { this.products = products; }

    public int getRecentlyUpdatedProductCount() { return recentlyUpdatedProductCount; }
    public void setRecentlyUpdatedProductCount(int recentlyUpdatedProductCount) {
        this.recentlyUpdatedProductCount = recentlyUpdatedProductCount;
    }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }

    public int getTotalProductCount() { return totalProductCount; }
    public void setTotalProductCount(int totalProductCount) {
        this.totalProductCount = totalProductCount;
    }
}
