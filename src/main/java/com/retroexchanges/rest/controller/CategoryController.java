package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.model.Category;
import com.retroexchanges.rest.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;
    
    @CrossOrigin(origins = "*")
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/category")
    public Category createCategory(@Valid @RequestBody Category category) throws IOException {
        Category c = new Category();
        c.setName(category.getName());
        c.setDescription(category.getDescription());
        c.setImage(category.getImage());
    	return categoryRepository.save(c);
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Category %d not found",categoryId)));
    }
    
    @CrossOrigin(origins = "*")
    @PutMapping("/category/{id}")
    public Category updateCategory(@Valid @RequestBody Category category,@PathVariable(value = "id") Long categoryId) throws IOException  {
    	
    	long id = categoryId;
        Category c = categoryRepository.findById(id)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Category %d not found",categoryId)));

        c.setName(category.getName());
        c.setDescription(category.getDescription());
        c.setImage(category.getImage());

        Category updatedCategory = categoryRepository.save(c);
        return updatedCategory;
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Category %d not found",categoryId)));

        categoryRepository.delete(category);

        return ResponseEntity.ok().build();
    }
}
