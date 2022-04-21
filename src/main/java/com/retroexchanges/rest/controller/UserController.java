package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.enumeration.UserStatus;
import com.retroexchanges.rest.exception.ResourceNotFoundException;
import com.retroexchanges.rest.json.Login;
import com.retroexchanges.rest.model.User;
import com.retroexchanges.rest.model.UserToken;
import com.retroexchanges.rest.repository.UserRepository;
import com.retroexchanges.rest.repository.UserTokenRepository;
import com.retroexchanges.rest.json.Login;
import com.retroexchanges.rest.security.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserTokenRepository userTokenRepository;
    
	@PostMapping("/login")
	public UserToken login(@RequestBody Login login) {
		
		User user = userRepository.findById(login.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "email", login.getEmail()));
		
		String email = user.getEmail();
		String password = user.getPassword();
		String login_password = login.getPassword(); 
		
		UserToken userToken = new UserToken();
		
		if (password.equals(login_password))
		{
			RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
			String token = authorization.getJWTToken(user.getEmail(),user.getIsAdmin());
			
			
			userToken.setEmail(email);
			userToken.setToken(token);
			
			userToken = userTokenRepository.save(userToken);
			
		}
		return userToken;
	}
	@PostMapping("/logout")
	public UserToken logout(@RequestBody Login login) {
		
		User user = userRepository.findById(login.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "email", login.getEmail()));
		
		String email = user.getEmail();
		String password = user.getPassword();
		String login_password = login.getPassword(); 
		
		UserToken userToken = new UserToken();
		
		if (password.equals(login_password))
		{
			RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
			String token = authorization.getJWTToken(user.getEmail(),user.getIsAdmin());
			
			
			userToken.setEmail(email);
			userToken.setToken(token);
			
			userToken = userTokenRepository.save(userToken);
			
		}
		return userToken;
	}
	
	
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/user/{email}")
    public User getUserById(@PathVariable(value = "email") String email) {
        return userRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @PutMapping("/user/{email}")
    public User updateUser(@PathVariable(value = "email") String email,
                                           @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setEmail(userDetails.getEmail());
        user.setAddress(userDetails.getAddress());
    	user.setPassword(userDetails.getPassword());
    	user.setNif(userDetails.getNif());
    	user.setStatus(userDetails.getStatus());
    	        
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "email") String email) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
