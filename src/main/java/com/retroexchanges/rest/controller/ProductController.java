package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.exception.ForbidenResourceException;
import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.exception.BadRequestException;
import com.retroexchanges.rest.model.Product;
import com.retroexchanges.rest.model.Category;
import com.retroexchanges.rest.repository.ProductRepository;
import com.retroexchanges.rest.repository.CategoryRepository;
import com.retroexchanges.rest.security.RetroexchangesAuthorizationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;

import com.retroexchanges.rest.model.ProductPicture;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    @CrossOrigin(origins = "*")
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAllOrderByUpdatedAtDesc();
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/products/{type}/{value}")
    public List<Product> getAllUserProducts(@PathVariable(value = "type") String type,@PathVariable(value = "value") String value) throws IOException  {
        switch(type) {
        	case "user":
        		List<Product> lpu = productRepository.findAllByOwner(value);
        		if (lpu.size() == 0) {
        				throw new RecordNotFoundException(String.format("Product not found with owner %s",value));
        		}
        		else { 
        			return lpu;
        		}
        	case "category":
        		Category category = categoryRepository.findById(Long.parseLong(value)).orElseThrow(() -> new RecordNotFoundException(String.format("Category %d not found",Long.parseLong(value))));
        		
        		List<Product> lpc =productRepository.findAllByCategory(category);
        		if (lpc.size() == 0) {
        			throw new RecordNotFoundException(String.format("Product not found with category %d",Integer.parseInt(value)));
        		}else {
        			return lpc;
        		}
        	case "name":
        		List<Product> lpn  = productRepository.findByNameContainingIgnoreCase(value);
        		if (lpn.size() == 0) {
        			throw new RecordNotFoundException(String.format("Product not found with name %s",value));
        		}else {
        			return lpn;
        		}
        }
        throw new RecordNotFoundException(String.format("Product not found"));
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/product")
    public Product createProduct(@RequestHeader("authorization") String header, @Valid @RequestBody Product product) throws IOException {
        //Damos de alta el producto
        try {

	    	// Solo dejamos dar de alta productos asociados al usuario autenticado
	    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
	    	Claims claims = authorization.decodeToken(header);
	    	String tokenUser = claims.getSubject();
	    	
	    	if (tokenUser.equals(product.getOwner())) {
	    	//Creamos un nuevo producto
	    	Product p = new Product();
	        //Asignamos los valores que nos pasan en el json
	    	p.setName(product.getName());
	        p.setDescription(product.getDescription());
	        p.setStatus(product.getStatus());
	        p.setOwner(product.getOwner());
	        if (product.getCategory()!=null) {
	        p.setCategory(product.getCategory());
	        }else {
	        	throw new BadRequestException("400 Bad request");	
	        }
	        p.setPrice(product.getPrice());

        	Product newProduct = productRepository.save(p);
        
	        // Recuperamos el identificador del producto
	        Long productId = newProduct.getProductId();
	        p = productRepository.findById(productId)
				.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found",productId)));
	        
	        // Y guardamos las imagenes asociandolas al producto ya dado de alta
	        List<ProductPicture> pictureList = product.getPictureList();
	        for(int index=0;index<pictureList.size();index++) {
	        	ProductPicture productPicture = pictureList.get(index);
	        	productPicture.setProductId(productId);
	        }
	        // Guardamos el producto con sus imagenes y lo retornamos al cliente
	        p.setPictureList(pictureList); 
	        return productRepository.save(p);
	    	}else {
	    		throw new ForbidenResourceException("Forbidden operation");
	    	}
    	}catch(Exception ex) {
        	throw new BadRequestException("400 Bad request");
        }
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable(value = "id") Long productId) {
        return productRepository.findById(productId)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found",productId)));
        
    }
    
    @CrossOrigin(origins = "*")
    @PutMapping("/product/{id}")
    public Product updateProduct(@RequestHeader("authorization") String header, @PathVariable(value = "id") Long productId,
                                           @Valid @RequestBody Product productDetails) {

        Product product = productRepository.findById(productId)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found",productId)));

        // Solo dejamos actualizar productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	if (tokenUser.equals(product.getOwner())) {
	        product.setName(productDetails.getName());
	        product.setDescription(productDetails.getDescription());
	        product.setOwner(productDetails.getOwner());
	        product.setPrice(productDetails.getPrice());
	        product.setPictureList(productDetails.getPictureList());
	        product.setCategory(productDetails.getCategory());
	        product.setStatus(productDetails.getStatus());
	        
	        Product updatedProduct = productRepository.save(product);
	        return updatedProduct;
    	}else {
    		throw new ForbidenResourceException("Forbidden operation");
    	}
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteNote(@RequestHeader("authorization") String header, @PathVariable(value = "id") Long productId) {
        Product product = productRepository.findById(productId)
        		.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found",productId)));

     // Solo dejamos actualizar productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	if (tokenUser.equals(product.getOwner())) {
    		productRepository.delete(product);
    		return ResponseEntity.ok().build();
    	}else {
    		throw new ForbidenResourceException("Forbidden operation");
    	}
        
    }
}
