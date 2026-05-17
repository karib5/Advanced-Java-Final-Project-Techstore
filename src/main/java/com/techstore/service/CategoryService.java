package com.techstore.service;

import com.techstore.dto.request.CategoryRequest;
import com.techstore.exception.ResourceNotFoundException;
import com.techstore.model.Category;
import com.techstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .toList();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setCreatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequest request) {
        Category existing = getCategoryById(id);
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        return categoryRepository.save(existing);
    }

    public void deleteCategory(Long id) {
        getCategoryById(id);
        categoryRepository.deleteById(id);
    }
}
