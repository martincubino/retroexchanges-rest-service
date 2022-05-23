package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.enumeration.UserStatus;
import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.exception.RecordAlreadyExistException;
import com.retroexchanges.rest.exception.AuthenticationErrorException;
import com.retroexchanges.rest.exception.ForbidenResourceException;
import com.retroexchanges.rest.json.Login;
import com.retroexchanges.rest.json.UserToken;
import com.retroexchanges.rest.model.Product;
import com.retroexchanges.rest.model.Favorite;
import com.retroexchanges.rest.model.FavoritePK;
import com.retroexchanges.rest.model.User;
import com.retroexchanges.rest.repository.FavoriteRepository;
import com.retroexchanges.rest.repository.ProductRepository;
import com.retroexchanges.rest.repository.UserRepository;
import com.retroexchanges.rest.security.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 36000)
public class FavoriteController {

	@Autowired
	FavoriteRepository favoriteRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/favorite")
	public Favorite createFavorite(@RequestHeader("authorization") String header, @RequestBody FavoritePK favorite) throws IOException {
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	String email = favorite.getEmail();
		Long productId = favorite.getProductId();
    	
		if (tokenUser.equals(email)) 
    	{
    			User user = userRepository.findById(email)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
		
    		Product product = productRepository.findById(productId)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found", productId)));

    		Favorite fav = new Favorite(email,productId);
    		fav.setUser(user);
    		fav.setProduct(product);
    		
    		return favoriteRepository.save(fav);
		} 
    	else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/favorites/{email}")
	public List<Favorite> getFavorites(@RequestHeader("authorization") String header, @PathVariable(value = "email") String email){
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	if (tokenUser.equals(email)) 
    	{
    	
		User user = userRepository.findById(email)
				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
	
		return favoriteRepository.findAllByUser(user);
		
    	}else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/favorite/{productId}")
	public Favorite getFavorite(@RequestHeader("authorization") String header, @PathVariable(value = "productId") Long productId)throws IOException {
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	String email = tokenUser;

    	User user = userRepository.findById(email)
				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
	
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found", productId)));

		FavoritePK favPK = new FavoritePK(email,productId);
		
		return favoriteRepository.findById(favPK).orElseThrow(() -> new RecordNotFoundException(String.format("Favorite %s-%d not found", email,productId)));

	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping("/favorite")
	public Favorite deleteFavorite(@RequestHeader("authorization") String header, @RequestBody FavoritePK favorite) throws IOException {
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	String email = favorite.getEmail();
		Long productId = favorite.getProductId();
    	
		if (tokenUser.equals(email)) 
    	{
    			User user = userRepository.findById(email)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
		
    		Product product = productRepository.findById(productId)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found", productId)));

    		Favorite fav = new Favorite(email,productId);
    		fav.setUser(user);
    		fav.setProduct(product);
    		
    		favoriteRepository.delete(fav);
    		return fav;
		} 
    	else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}

}
