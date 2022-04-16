package com.retroexchanges.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retroexchanges.rest.model.Category;

/**
 * Created by fjmartincubino
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
