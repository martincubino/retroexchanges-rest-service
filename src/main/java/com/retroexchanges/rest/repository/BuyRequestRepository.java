package com.retroexchanges.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.retroexchanges.rest.model.BuyRequest;
import com.retroexchanges.rest.model.Product;
import com.retroexchanges.rest.model.User;

/**
 * Created by fjmartincubino
 */

@Repository
public interface BuyRequestRepository extends JpaRepository<BuyRequest, Long> {

	List<BuyRequest> findAllBySellerOrderByStatus(User seller);
	List<BuyRequest> findAllByBuyer(User buyer);
	List<BuyRequest> findAllByProduct(Product product);
}
