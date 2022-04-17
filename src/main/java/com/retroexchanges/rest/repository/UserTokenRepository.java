package com.retroexchanges.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retroexchanges.rest.model.UserToken;

/**
 * Created by fjmartincubino
 */

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {

}
