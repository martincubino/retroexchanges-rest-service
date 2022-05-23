package com.retroexchanges.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retroexchanges.rest.model.Product;
import com.retroexchanges.rest.model.Category;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * Created by fjmartincubino
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT * FROM products ORDER BY updated_at DESC", nativeQuery = true)
	List<Product> findAllOrderByUpdatedAtDesc();
	
	List<Product> findAllByOwner(String owner);
	
	List<Product> findAllByCategory(Category category);
	
	List<Product> findByNameContainingIgnoreCase(String name);
	
	
	
}
