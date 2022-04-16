package com.retroexchanges.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retroexchanges.rest.model.BuyRequest;

/**
 * Created by fjmartincubino
 */

@Repository
public interface BuyRequestRepository extends JpaRepository<BuyRequest, Long> {

}
