package com.nisum.productmanagement.service;

import com.nisum.productmanagement.model.Category;
import com.nisum.productmanagement.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;
    private Category updatedCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(1);
        testCategory.setCategoryName("Electronics");
        testCategory.setDescription("Electronic devices and gadgets");
        testCategory.setCategoryImage("electronics.jpg");

        updatedCategory = new Category();
        updatedCategory.setCategoryName("Updated Electronics");
        updatedCategory.setDescription("Updated electronic devices and gadgets");
        updatedCategory.setCategoryImage("updated_electronics.jpg");
    }

    @Test
    void getAllCategories_WhenCategoriesExist_ReturnsCategoriesList() {
        // Arrange
        Category category2 = new Category();
        category2.setId(2);
        category2.setCategoryName("Books");
        category2.setDescription("Books and literature");

        List<Category> expectedCategories = Arrays.asList(testCategory, category2);
        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        // Act
        List<Category> actualCategories = categoryService.getAllCategories();

        // Assert
        assertNotNull(actualCategories);
        assertEquals(2, actualCategories.size());
        assertEquals(expectedCategories, actualCategories);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategories_WhenNoCategoriesExist_ReturnsEmptyList() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Category> actualCategories = categoryService.getAllCategories();

        // Assert
        assertNotNull(actualCategories);
        assertTrue(actualCategories.isEmpty());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategories_WhenDatabaseError_ThrowsRuntimeException() {
        // Arrange
        when(categoryRepository.findAll()).thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> categoryService.getAllCategories());
        
        assertEquals("Failed to fetch categories from database", exception.getMessage());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_WhenCategoryExists_ReturnsCategory() {
        // Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));

        // Act
        Optional<Category> result = categoryService.getCategoryById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testCategory, result.get());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void getCategoryById_WhenCategoryDoesNotExist_ReturnsEmpty() {
        // Arrange
        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Category> result = categoryService.getCategoryById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(999);
    }

    @Test
    void getCategoryById_WhenDatabaseError_ThrowsRuntimeException() {
        // Arrange
        when(categoryRepository.findById(anyInt())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> categoryService.getCategoryById(1));
        
        assertEquals("Failed to fetch category by ID", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void getCategoryByName_WhenCategoryExists_ReturnsCategory() {
        // Arrange
        String categoryName = "Electronics";
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName))
            .thenReturn(Optional.of(testCategory));

        // Act
        Optional<Category> result = categoryService.getCategoryByName(categoryName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testCategory, result.get());
        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase(categoryName);
    }

    @Test
    void getCategoryByName_WhenCategoryDoesNotExist_ReturnsEmpty() {
        // Arrange
        String categoryName = "NonExistent";
        when(categoryRepository.findByCategoryNameIgnoreCase(categoryName))
            .thenReturn(Optional.empty());

        // Act
        Optional<Category> result = categoryService.getCategoryByName(categoryName);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase(categoryName);
    }

    @Test
    void getCategoryByName_WhenDatabaseError_ThrowsRuntimeException() {
        // Arrange
        when(categoryRepository.findByCategoryNameIgnoreCase(anyString()))
            .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> categoryService.getCategoryByName("Electronics"));
        
        assertEquals("Failed to search category by name", exception.getMessage());
        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase("Electronics");
    }

    @Test
    void createCategory_WhenValidCategory_ReturnsCreatedCategory() {
        // Arrange
        Category newCategory = new Category();
        newCategory.setCategoryName("Books");
        newCategory.setDescription("Books and literature");

        Category savedCategory = new Category();
        savedCategory.setId(2);
        savedCategory.setCategoryName("Books");
        savedCategory.setDescription("Books and literature");

        when(categoryRepository.existsByCategoryNameIgnoreCase("Books")).thenReturn(false);
        when(categoryRepository.save(newCategory)).thenReturn(savedCategory);

        // Act
        Category result = categoryService.createCategory(newCategory);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Books", result.getCategoryName());
        verify(categoryRepository, times(1)).existsByCategoryNameIgnoreCase("Books");
        verify(categoryRepository, times(1)).save(newCategory);
    }

    @Test
    void createCategory_WhenCategoryNameIsNull_ThrowsIllegalArgumentException() {
        // Arrange
        Category invalidCategory = new Category();
        invalidCategory.setCategoryName(null);
        invalidCategory.setDescription("Some description");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> categoryService.createCategory(invalidCategory));
        
        assertEquals("Category name cannot be null or empty", exception.getMessage());
        verify(categoryRepository, never()).existsByCategoryNameIgnoreCase(anyString());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void createCategory_WhenCategoryNameIsEmpty_ThrowsIllegalArgumentException() {
        // Arrange
        Category invalidCategory = new Category();
        invalidCategory.setCategoryName("   ");
        invalidCategory.setDescription("Some description");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> categoryService.createCategory(invalidCategory));
        
        assertEquals("Category name cannot be null or empty", exception.getMessage());
        verify(categoryRepository, never()).existsByCategoryNameIgnoreCase(anyString());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void createCategory_WhenCategoryAlreadyExists_ThrowsRuntimeException() {
        // Arrange
        Category duplicateCategory = new Category();
        duplicateCategory.setCategoryName("Electronics");
        duplicateCategory.setDescription("Duplicate category");

        when(categoryRepository.existsByCategoryNameIgnoreCase("Electronics")).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> categoryService.createCategory(duplicateCategory));
        
        assertEquals("A category with name 'Electronics' already exists", exception.getMessage());
        verify(categoryRepository, times(1)).existsByCategoryNameIgnoreCase("Electronics");
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void createCategory_WhenDatabaseError_ThrowsRuntimeException() {
        // Arrange
        Category newCategory = new Category();
        newCategory.setCategoryName("Books");
        newCategory.setDescription("Books and literature");

        when(categoryRepository.existsByCategoryNameIgnoreCase("Books")).thenReturn(false);
        when(categoryRepository.save(newCategory)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> categoryService.createCategory(newCategory));
        
        assertEquals("Database error", exception.getMessage());
        verify(categoryRepository, times(1)).existsByCategoryNameIgnoreCase("Books");
        verify(categoryRepository, times(1)).save(newCategory);
    }

    @Test
    void updateCategory_WhenCategoryExists_ReturnsUpdatedCategory() {
        // Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.findByCategoryNameIgnoreCase("Updated Electronics"))
            .thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        // Act
        Optional<Category> result = categoryService.updateCategory(1, updatedCategory);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Electronics", testCategory.getCategoryName());
        assertEquals("Updated electronic devices and gadgets", testCategory.getDescription());
        assertEquals("updated_electronics.jpg", testCategory.getCategoryImage());
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase("Updated Electronics");
        verify(categoryRepository, times(1)).save(testCategory);
    }

    @Test
    void updateCategory_WhenCategoryDoesNotExist_ReturnsEmpty() {
        // Arrange
        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Category> result = categoryService.updateCategory(999, updatedCategory);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(999);
        verify(categoryRepository, never()).findByCategoryNameIgnoreCase(anyString());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategory_WhenCategoryNameIsNull_ThrowsIllegalArgumentException() {
        // Arrange
        Category invalidUpdate = new Category();
        invalidUpdate.setCategoryName(null);
        invalidUpdate.setDescription("Some description");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> categoryService.updateCategory(1, invalidUpdate));
        
        assertEquals("Category name cannot be null or empty", exception.getMessage());
        verify(categoryRepository, never()).findById(anyInt());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategory_WhenCategoryNameIsEmpty_ThrowsIllegalArgumentException() {
        // Arrange
        Category invalidUpdate = new Category();
        invalidUpdate.setCategoryName("   ");
        invalidUpdate.setDescription("Some description");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> categoryService.updateCategory(1, invalidUpdate));
        
        assertEquals("Category name cannot be null or empty", exception.getMessage());
        verify(categoryRepository, never()).findById(anyInt());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategory_WhenAnotherCategoryWithSameNameExists_ThrowsRuntimeException() {
        // Arrange
        Category anotherCategory = new Category();
        anotherCategory.setId(2);
        anotherCategory.setCategoryName("Updated Electronics");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.findByCategoryNameIgnoreCase("Updated Electronics"))
            .thenReturn(Optional.of(anotherCategory));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> categoryService.updateCategory(1, updatedCategory));
        
        assertEquals("Another category with name 'Updated Electronics' already exists", 
            exception.getMessage());
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).findByCategoryNameIgnoreCase("Updated Electronics");
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategory_WhenDatabaseError_ThrowsRuntimeException() {
        // Arrange
        when(categoryRepository.findById(1)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> categoryService.updateCategory(1, updatedCategory));
        
        assertEquals("Database error", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1);
    }

    @Test
    void deleteCategory_WhenCategoryExists_ReturnsTrue() {
        // Arrange
        when(categoryRepository.existsById(1)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1);

        // Act
        boolean result = categoryService.deleteCategory(1);

        // Assert
        assertTrue(result);
        verify(categoryRepository, times(1)).existsById(1);
        verify(categoryRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteCategory_WhenCategoryDoesNotExist_ReturnsFalse() {
        // Arrange
        when(categoryRepository.existsById(999)).thenReturn(false);

        // Act
        boolean result = categoryService.deleteCategory(999);

        // Assert
        assertFalse(result);
        verify(categoryRepository, times(1)).existsById(999);
        verify(categoryRepository, never()).deleteById(anyInt());
    }

    @Test
    void deleteCategory_WhenDatabaseError_ThrowsRuntimeException() {
        // Arrange
        when(categoryRepository.existsById(1)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> categoryService.deleteCategory(1));
        
        assertEquals("Failed to delete category", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(1);
        verify(categoryRepository, never()).deleteById(anyInt());
    }
}