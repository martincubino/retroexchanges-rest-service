package com.retroexchanges.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retroexchanges.rest.model.Rating;
import com.retroexchanges.rest.model.RatingPK;
import com.retroexchanges.rest.model.User;

import java.util.List;

/**
 * Created by fjmartincubino
 */

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingPK> {
	
	List<Rating> findAllByUserRated(User user);
	List<Rating> findAllByUserWhoRate(User user);

}
