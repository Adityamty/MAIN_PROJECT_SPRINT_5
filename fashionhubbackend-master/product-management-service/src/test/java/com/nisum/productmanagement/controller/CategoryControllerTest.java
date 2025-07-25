package com.nisum.productmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.productmanagement.model.Category;
import com.nisum.productmanagement.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private Category testCategory;
    private Category testCategory2;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(1);
        testCategory.setCategoryName("Electronics");
        testCategory.setDescription("Electronic devices and gadgets");
        testCategory.setCategoryImage("electronics.jpg");

        testCategory2 = new Category();
        testCategory2.setId(2);
        testCategory2.setCategoryName("Books");
        testCategory2.setDescription("Books and literature");
        testCategory2.setCategoryImage("books.jpg");
    }

    @Test
    void getAllCategories_WhenCategoriesExist_ReturnsOkWithCategoriesList() throws Exception {
        // Arrange
        List<Category> categories = Arrays.asList(testCategory, testCategory2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        // Act & Assert
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].categoryName").value("Electronics"))
                .andExpect(jsonPath("$[0].description").value("Electronic devices and gadgets"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].categoryName").value("Books"));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getAllCategories_WhenNoCategoriesExist_ReturnsOkWithEmptyList() throws Exception {
        // Arrange
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getAllCategories_WhenServiceThrowsException_ReturnsInternalServerError() throws Exception {
        // Arrange
        when(categoryService.getAllCategories()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(get("/categories"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error fetching categories")));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getCategoryById_WhenCategoryExists_ReturnsOkWithCategory() throws Exception {
        // Arrange
        when(categoryService.getCategoryById(1)).thenReturn(Optional.of(testCategory));

        // Act & Assert
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.categoryName").value("Electronics"))
                .andExpect(jsonPath("$.description").value("Electronic devices and gadgets"));

        verify(categoryService, times(1)).getCategoryById(1);
    }

    @Test
    void getCategoryById_WhenCategoryDoesNotExist_ReturnsNotFound() throws Exception {
        // Arrange
        when(categoryService.getCategoryById(999)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/categories/999"))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryById(999);
    }

    @Test
    void getCategoryById_WhenServiceThrowsException_ReturnsInternalServerError() throws Exception {
        // Arrange
        when(categoryService.getCategoryById(1)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error fetching category")));

        verify(categoryService, times(1)).getCategoryById(1);
    }

    @Test
    void createCategory_WhenValidCategory_ReturnsCreatedWithCategory() throws Exception {
        // Arrange
        Category newCategory = new Category();
        newCategory.setCategoryName("Sports");
        newCategory.setDescription("Sports equipment");

        Category savedCategory = new Category();
        savedCategory.setId(3);
        savedCategory.setCategoryName("Sports");
        savedCategory.setDescription("Sports equipment");

        when(categoryService.createCategory(any(Category.class))).thenReturn(savedCategory);

        // Act & Assert
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.categoryName").value("Sports"))
                .andExpect(jsonPath("$.description").value("Sports equipment"));

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void createCategory_WhenCategoryNameIsNull_ReturnsBadRequest() throws Exception {
        // Arrange
        Category invalidCategory = new Category();
        invalidCategory.setCategoryName(null);
        invalidCategory.setDescription("Some description");

        // Act & Assert
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCategory)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Category name is required"));

        verify(categoryService, never()).createCategory(any());
    }

    @Test
    void createCategory_WhenCategoryNameIsEmpty_ReturnsBadRequest() throws Exception {
        // Arrange
        Category invalidCategory = new Category();
        invalidCategory.setCategoryName("   ");
        invalidCategory.setDescription("Some description");

        // Act & Assert
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCategory)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Category name is required"));

        verify(categoryService, never()).createCategory(any());
    }

    @Test
    void createCategory_WhenCategoryAlreadyExists_ReturnsConflict() throws Exception {
        // Arrange
        Category duplicateCategory = new Category();
        duplicateCategory.setCategoryName("Electronics");
        duplicateCategory.setDescription("Duplicate category");

        when(categoryService.createCategory(any(Category.class)))
            .thenThrow(new RuntimeException("A category with name 'Electronics' already exists"));

        // Act & Assert
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateCategory)))
                .andExpect(status().isConflict())
                .andExpect(content().string("A category with name 'Electronics' already exists"));

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void createCategory_WhenServiceThrowsUnexpectedException_ReturnsInternalServerError() throws Exception {
        // Arrange
        Category newCategory = new Category();
        newCategory.setCategoryName("Sports");
        newCategory.setDescription("Sports equipment");

        when(categoryService.createCategory(any(Category.class)))
            .thenThrow(new RuntimeException("Unexpected database error"));

        // Act & Assert
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error creating category")));

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    void updateCategory_WhenValidUpdate_ReturnsOkWithUpdatedCategory() throws Exception {
        // Arrange
        Category updateCategory = new Category();
        updateCategory.setCategoryName("Updated Electronics");
        updateCategory.setDescription("Updated description");

        Category updatedCategory = new Category();
        updatedCategory.setId(1);
        updatedCategory.setCategoryName("Updated Electronics");
        updatedCategory.setDescription("Updated description");

        when(categoryService.updateCategory(eq(1), any(Category.class)))
            .thenReturn(Optional.of(updatedCategory));

        // Act & Assert
        mockMvc.perform(put("/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCategory)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.categoryName").value("Updated Electronics"))
                .andExpect(jsonPath("$.description").value("Updated description"));

        verify(categoryService, times(1)).updateCategory(eq(1), any(Category.class));
    }

    @Test
    void updateCategory_WhenCategoryDoesNotExist_ReturnsNotFound() throws Exception {
        // Arrange
        Category updateCategory = new Category();
        updateCategory.setCategoryName("Updated Electronics");
        updateCategory.setDescription("Updated description");

        when(categoryService.updateCategory(eq(999), any(Category.class)))
            .thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/categories/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCategory)))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).updateCategory(eq(999), any(Category.class));
    }

    @Test
    void updateCategory_WhenCategoryNameIsNull_ReturnsBadRequest() throws Exception {
        // Arrange
        Category invalidUpdate = new Category();
        invalidUpdate.setCategoryName(null);
        invalidUpdate.setDescription("Some description");

        // Act & Assert
        mockMvc.perform(put("/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUpdate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Category name is required"));

        verify(categoryService, never()).updateCategory(anyInt(), any());
    }

    @Test
    void deleteCategory_WhenCategoryExists_ReturnsNoContent() throws Exception {
        // Arrange
        when(categoryService.deleteCategory(1)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategory(1);
    }

    @Test
    void deleteCategory_WhenCategoryDoesNotExist_ReturnsNotFound() throws Exception {
        // Arrange
        when(categoryService.deleteCategory(999)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/categories/999"))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).deleteCategory(999);
    }

    @Test
    void deleteCategory_WhenServiceThrowsException_ReturnsInternalServerError() throws Exception {
        // Arrange
        when(categoryService.deleteCategory(1)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error deleting category")));

        verify(categoryService, times(1)).deleteCategory(1);
    }

    @Test
    void healthCheck_WhenServiceIsHealthy_ReturnsOk() throws Exception {
        // Arrange
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/categories/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Category service is running and database is connected"));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void healthCheck_WhenServiceIsUnhealthy_ReturnsInternalServerError() throws Exception {
        // Arrange
        when(categoryService.getAllCategories()).thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        mockMvc.perform(get("/categories/health"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("database connection failed")));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void getCategoryByName_WhenCategoryExists_ReturnsOkWithCategory() throws Exception {
        // Arrange
        when(categoryService.getCategoryByName("Electronics")).thenReturn(Optional.of(testCategory));

        // Act & Assert
        mockMvc.perform(get("/categories/search")
                .param("name", "Electronics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.categoryName").value("Electronics"));

        verify(categoryService, times(1)).getCategoryByName("Electronics");
    }

    @Test
    void getCategoryByName_WhenCategoryDoesNotExist_ReturnsNotFound() throws Exception {
        // Arrange
        when(categoryService.getCategoryByName("NonExistent")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/categories/search")
                .param("name", "NonExistent"))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryByName("NonExistent");
    }
}