package com.nisum.productmanagement.service;

import com.nisum.productmanagement.model.Category;
import com.nisum.productmanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        try {
            System.out.println("Fetching all categories from database...");
            List<Category> categories = categoryRepository.findAll();
            System.out.println("Found " + categories.size() + " categories in database");
            return categories;
        } catch (Exception e) {
            System.err.println("Error fetching categories from database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch categories from database", e);
        }
    }

    public Optional<Category> getCategoryById(int id) {
        try {
            System.out.println("Fetching category with ID: " + id);
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                System.out.println("Found category: " + category.get().getCategoryName());
            } else {
                System.out.println("No category found with ID: " + id);
            }
            return category;
        } catch (Exception e) {
            System.err.println("Error fetching category by ID: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch category by ID", e);
        }
    }

    public Optional<Category> getCategoryByName(String name) {
        try {
            System.out.println("Searching for category with name: " + name);
            Optional<Category> category = categoryRepository.findByCategoryNameIgnoreCase(name);
            if (category.isPresent()) {
                System.out.println("Found category with name: " + name);
            } else {
                System.out.println("No category found with name: " + name);
            }
            return category;
        } catch (Exception e) {
            System.err.println("Error searching category by name: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to search category by name", e);
        }
    }

    public Category createCategory(Category category) {
        try {
            System.out.println("Creating new category: " + category.getCategoryName());
            
            // Validate input
            if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
                throw new IllegalArgumentException("Category name cannot be null or empty");
            }
            
            // Check if category with the same name already exists
            if (categoryRepository.existsByCategoryNameIgnoreCase(category.getCategoryName())) {
                throw new RuntimeException("A category with name '" + category.getCategoryName() + "' already exists");
            }
            
            Category savedCategory = categoryRepository.save(category);
            System.out.println("Category created successfully with ID: " + savedCategory.getId());
            return savedCategory;
        } catch (RuntimeException e) {
            System.err.println("Runtime error creating category: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error creating category: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create category", e);
        }
    }
    
    public Optional<Category> updateCategory(int id, Category updatedCategory) {
        try {
            System.out.println("Updating category with ID: " + id);
            
            // Validate input
            if (updatedCategory.getCategoryName() == null || updatedCategory.getCategoryName().trim().isEmpty()) {
                throw new IllegalArgumentException("Category name cannot be null or empty");
            }
            
            Optional<Category> existingCategory = categoryRepository.findById(id);
            
            if (existingCategory.isPresent()) {
                // Check if another category with the same name already exists (but not the one we're updating)
                Optional<Category> categoryWithSameName = categoryRepository.findByCategoryNameIgnoreCase(updatedCategory.getCategoryName());
                if (categoryWithSameName.isPresent() && categoryWithSameName.get().getId() != id) {
                    throw new RuntimeException("Another category with name '" + updatedCategory.getCategoryName() + "' already exists");
                }
                
                Category category = existingCategory.get();
                category.setCategoryName(updatedCategory.getCategoryName());
                category.setDescription(updatedCategory.getDescription());
                category.setCategoryImage(updatedCategory.getCategoryImage());
                
                Category savedCategory = categoryRepository.save(category);
                System.out.println("Category updated successfully");
                return Optional.of(savedCategory);
            } else {
                System.out.println("Category not found with ID: " + id);
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            System.err.println("Runtime error updating category: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error updating category: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update category", e);
        }
    }
    
    public boolean deleteCategory(int id) {
        try {
            System.out.println("Deleting category with ID: " + id);
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
                System.out.println("Category deleted successfully");
                return true;
            } else {
                System.out.println("Category not found with ID: " + id);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error deleting category: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete category", e);
        }
    }
}
