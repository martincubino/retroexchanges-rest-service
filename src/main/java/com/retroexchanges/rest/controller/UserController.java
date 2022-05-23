package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.enumeration.UserStatus;
import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.exception.RecordAlreadyExistException;
import com.retroexchanges.rest.exception.AuthenticationErrorException;
import com.retroexchanges.rest.exception.ForbidenResourceException;
import com.retroexchanges.rest.json.Login;
import com.retroexchanges.rest.json.UserToken;
import com.retroexchanges.rest.model.User;
import com.retroexchanges.rest.repository.UserRepository;
import com.retroexchanges.rest.security.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@PostMapping("/login")
	public UserToken login(@RequestBody Login login) {

		String username = login.getEmail();
		User user = userRepository.findById(username)
				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", username)));

		String password = user.getPassword();
		String login_password = login.getPassword();

		if (password.equals(login_password)) {
			RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();

			UserToken userToken = authorization.getJWTToken(user.getEmail(), user.getIsAdmin());
 			userToken.setIsAdmin(user.getIsAdmin());
			return userToken;

		} else {
			throw new AuthenticationErrorException("Failed to authenticate");
		}
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@PostMapping("/register")
	public UserToken createUser(@Valid @RequestBody User user) {

		User userInDatabase = null;
		try {
			Optional<User> u = userRepository.findById(user.getEmail());
			if (!u.isEmpty()) {
				userInDatabase = u.get();
			}

		} catch (Exception ex) {

		}
		User newUser = new User();
		if (userInDatabase != null) {
			throw new RecordAlreadyExistException(String.format("User %s already exist", userInDatabase.getEmail()));
		} else {
			newUser.setEmail(user.getEmail());
			newUser.setPassword(user.getPassword());
			newUser.setName(user.getName());
			newUser.setSurname(user.getSurname());
			newUser.setStatus(UserStatus.ACTIVE);
			newUser.setIsAdmin(false);
		}

		userRepository.save(newUser);

		RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();

		UserToken userToken = authorization.getJWTToken(newUser.getEmail(), newUser.getIsAdmin());

		return userToken;
	}

	@GetMapping("/user/{email}")
    public User getUserById(@RequestHeader("authorization") String header, @PathVariable(value = "email") String email) {
		
		boolean administrator = false;
		RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	List<String> roles = claims.get("authorities",List.class);
    	if (roles.indexOf("ROLE_ADMIN")!=-1) {
    		administrator = true;
    	}
    	if (tokenUser.equals(email)||(administrator==true)) {
    		return userRepository.findById(email).orElseThrow(() -> 
    		new RecordNotFoundException(String.format("User %s not found",email)));
    	}else {
    		throw new ForbidenResourceException("Forbidden operation");
    	}
    }
	

	@PutMapping("/user/{email}")
	public User updateUser(@RequestHeader("authorization") String header, @PathVariable(value = "email") String email, @Valid @RequestBody User userDetails) {

		boolean administrator = false;
		RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	List<String> roles = claims.get("authorities",List.class);
    	if (roles.indexOf("ROLE_ADMIN")!=-1) {
    		administrator = true;
    	}
    	if (tokenUser.equals(email)||(administrator==true)) {

    		User user = userRepository.findById(email)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));

    		user.setName(userDetails.getName());
    		user.setSurname(userDetails.getSurname());
    		user.setEmail(userDetails.getEmail());
    		user.setAddress(userDetails.getAddress());
    		user.setPassword(userDetails.getPassword());
    		user.setNif(userDetails.getNif());
    		User updatedUser = userRepository.save(user);
    		
			return updatedUser;

    	}else {
    		throw new ForbidenResourceException("Forbidden operation");
    	}
    	

	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "email") String email) {
		User user = userRepository.findById(email)
				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));

		userRepository.delete(user);

		return ResponseEntity.ok().build();
	}
}
