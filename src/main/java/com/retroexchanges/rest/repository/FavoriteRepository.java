package com.retroexchanges.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retroexchanges.rest.model.Favorite;
import com.retroexchanges.rest.model.FavoritePK;
import com.retroexchanges.rest.model.User;

import java.util.List;

/**
 * Created by fjmartincubino
 */

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoritePK> {
	
	List<Favorite> findAllByUser(User user);

}
