package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.exception.AuthenticationErrorException;
import com.retroexchanges.rest.model.Rating;
import com.retroexchanges.rest.model.BuyRequest;
import com.retroexchanges.rest.model.User;
import com.retroexchanges.rest.json.UserRating;
import com.retroexchanges.rest.json.RatingRequest;
import com.retroexchanges.rest.repository.RatingRepository;

import com.retroexchanges.rest.repository.BuyRequestRepository;
import com.retroexchanges.rest.repository.UserRepository;
import com.retroexchanges.rest.security.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 36000)
public class RatingController {

	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	BuyRequestRepository buyRequestRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/rating")
	public Rating createRating(@RequestHeader("authorization") String header, @RequestBody RatingRequest rating) throws IOException {
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	String email = rating.getUserWhoRate();
    	String emailUserRated = rating.getUserRated();
    	Long requestId = rating.getBuyRequestId();
    	
		if (tokenUser.equals(email)) 
    	{
    		User user = userRepository.findById(email)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
		
    		BuyRequest buyRequest = buyRequestRepository.findById(requestId )
    				.orElseThrow(() -> new RecordNotFoundException(String.format("Request %d not found", requestId)));

    		Rating rat= new Rating(emailUserRated,email,requestId );
    		rat.setRating(rating.getRating());
    		
    		return ratingRepository.save(rat);
		} 
    	else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/rating/{email}")
	public UserRating getRating(@PathVariable(value = "email") String email){

  		User user = userRepository.findById(email)
				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
    		
    	List<Rating> rating = ratingRepository.findAllByUserRated(user);
    	
    	Double rate = 0.0;
    	int votes = rating.size();
    	for (int i=0;i<votes;i++) {
    		rate+=rating.get(i).getRating(); 
    	}
    	UserRating userRate = new UserRating();
    	userRate.setEmail(email);
    	userRate.setRating(rate/votes);
		return userRate;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/ratings/{email}")
	public List<Rating> getRatings(@RequestHeader("authorization") String header, @PathVariable(value = "email") String email){
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	if (tokenUser.equals(email)) 
    	{
    		User user = userRepository.findById(email)
				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
    		
    		List<Rating> rating = ratingRepository.findAllByUserRated(user);

    		return rating; 
		
    	}else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}
	

}
