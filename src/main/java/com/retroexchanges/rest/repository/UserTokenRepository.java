package com.retroexchanges.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.retroexchanges.rest.model.UserToken;

/**
 * Created by fjmartincubino
 */

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {

	  @Query(value = "select * from user_tokens u where u.email =  ?1 and timestampdiff(HOUR,u.create_at,now()) < 24 ", nativeQuery = true)
	  UserToken findTokenByUser(String email);
	  
	  @Query(value = "SELECT * from user_tokens u where timestampdiff(HOUR,u.create_at,now()) > 24 ", nativeQuery = true)
	  List<UserToken> findExpirated();
}
