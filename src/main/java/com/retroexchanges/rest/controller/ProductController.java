package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.enumeration.ProductStatus;
import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.json.CategoryDTO;
import com.retroexchanges.rest.model.Category;
import com.retroexchanges.rest.model.Product;
import com.retroexchanges.rest.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.retroexchanges.rest.json.ProductDTO;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 36000)
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    
    @CrossOrigin(origins = "*")
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/product")
    public Product createProduct( @Valid @RequestBody ProductDTO productDTO) throws IOException {
        Product p = new Product();
        p.setName(productDTO.getName());
        p.setDescription(productDTO.getDescription());
        p.setStatus(productDTO.getStatus());
        p.setOwner(productDTO.getOwner());
        long i = productDTO.getCategoryId();
        p.setCategoryId(i);
        p.setPrice(productDTO.getPrice());
        return productRepository.save(p);
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable(value = "id") Long productId) {
        return productRepository.findById(productId)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found",productId)));
        
    }

    @PutMapping("/product/{id}")
    public Product updateProduct(@PathVariable(value = "id") Long productId,
                                           @Valid @RequestBody Product productDetails) {

        Product product = productRepository.findById(productId)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found",productId)));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setOwner(productDetails.getOwner());
        product.setPrice(productDetails.getPrice());
        product.setCategoryId(productDetails.getCategoryId());
        product.setStatus(productDetails.getStatus());
        
        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long productId) {
        Product product = productRepository.findById(productId)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found",productId)));

        productRepository.delete(product);

        return ResponseEntity.ok().build();
    }
}
