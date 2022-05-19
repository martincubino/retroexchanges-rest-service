package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.model.Category;
import com.retroexchanges.rest.repository.CategoryRepository;
import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.json.CategoryDTO;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081/#/", maxAge = 36000)
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
    public Category createCategory(@ModelAttribute CategoryDTO categoryDTO) throws IOException {
        Category c = new Category();
        c.setName(categoryDTO.getName());
        c.setDescription(categoryDTO.getDescription());
        c.setImage(categoryDTO.getLogo().getBytes());
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
    public Category updateNote(@ModelAttribute CategoryDTO categoryDTO,@PathVariable(value = "id") Long categoryId) throws IOException  {
    	
    	long id = categoryId;
        Category category = categoryRepository.findById(id)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Category %d not found",categoryId)));

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setImage(categoryDTO.getLogo().getBytes());

        Category updatedCategory = categoryRepository.save(category);
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
