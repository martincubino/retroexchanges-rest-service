package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.exception.ResourceNotFoundException;
import com.retroexchanges.rest.model.Category;
import com.retroexchanges.rest.repository.CategoryRepository;
import com.retroexchanges.rest.json.CategoryDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("/category")
    public Category createCategory(@ModelAttribute CategoryDTO categoryDTO) throws IOException {
        Category c = new Category();
        c.setName(categoryDTO.getName());
        c.setDescription(categoryDTO.getDescription());
        c.setImage(categoryDTO.getLogo().getBytes());
    	return categoryRepository.save(c);
    }

    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }

    @PutMapping("/category/{id}")
    public Category updateNote(@PathVariable(value = "id") Long categoryId,
                                           @Valid @RequestBody Category categoryDetails) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setImage(categoryDetails.getImage());

        Category updatedCategory = categoryRepository.save(category);
        return updatedCategory;
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);

        return ResponseEntity.ok().build();
    }
}
