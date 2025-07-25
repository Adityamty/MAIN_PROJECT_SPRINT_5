package com.nisum.productmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto {
    private Long id;
    
    @JsonProperty("categoryName")
    private String name;
    
    private String categoryImage;
    private String description;

    public CategoryDto() {}

    public CategoryDto(Long id, String name, String categoryImage, String description) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategoryImage() { return categoryImage; }
    public void setCategoryImage(String categoryImage) { this.categoryImage = categoryImage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
