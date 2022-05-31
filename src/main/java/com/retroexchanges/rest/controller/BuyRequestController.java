package com.retroexchanges.rest.controller;

import com.retroexchanges.rest.exception.RecordNotFoundException;
import com.retroexchanges.rest.enumeration.RequestStatus;
import com.retroexchanges.rest.exception.AuthenticationErrorException;
import com.retroexchanges.rest.exception.ForbidenResourceException;
import com.retroexchanges.rest.model.Product;
import com.retroexchanges.rest.model.BuyRequest;
import com.retroexchanges.rest.model.User;
import com.retroexchanges.rest.json.Request;
import com.retroexchanges.rest.repository.BuyRequestRepository;
import com.retroexchanges.rest.repository.ProductRepository;
import com.retroexchanges.rest.repository.UserRepository;
import com.retroexchanges.rest.security.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BuyRequestController {

	@Autowired
	BuyRequestRepository buyRequestRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/request")
	public BuyRequest createRequest(@RequestHeader("authorization") String header, @RequestBody Request request) throws IOException {
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	String buyerEmail = request.getBuyer();
    	String sellerEmail = request.getSeller();
		Long productId = request.getProductId();
    	
		if (tokenUser.equals(buyerEmail )) 
    	{
    			User buyer = userRepository.findById(buyerEmail)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("Buyer %s not found", buyerEmail)));
    			User seller = userRepository.findById(sellerEmail)
        				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", sellerEmail)));
    			
    			Product product = productRepository.findById(productId)
    					.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found", productId)));

    			BuyRequest buyRequest = new BuyRequest(buyer,seller,product);
    			buyRequest.setPrice(request.getPrice());
    			buyRequest.setStatus(RequestStatus.PENDING);
    			
    			return buyRequestRepository.save(buyRequest);
		} 
    	else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/requests/buyer/{email}")
	public List<BuyRequest> getRequestBuyer(@RequestHeader("authorization") String header, @PathVariable(value = "email") String email){
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	if (tokenUser.equals(email)) 
    	{
    		User user = userRepository.findById(email)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
	
    		return buyRequestRepository.findAllByBuyer(user);
		
    	}else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/requests/seller/{email}")
	public List<BuyRequest> getRequestSeller(@RequestHeader("authorization") String header, @PathVariable(value = "email") String email){
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	if (tokenUser.equals(email)) 
    	{
    		User user = userRepository.findById(email)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
	
    		return buyRequestRepository.findAllBySellerOrderByStatus(user);
		
    	}else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}
	@CrossOrigin(origins = "*")
	@GetMapping("/request/product/{productId}")
	public List<BuyRequest> getRequestProduct(@RequestHeader("authorization") String header, @PathVariable(value = "email") Long productId){

		// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String email = claims.getSubject();
    	
   		User user = userRepository.findById(email)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
   		
   		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found", productId)));
	
		List<BuyRequest> listRequest = buyRequestRepository.findAllByProduct(product);
		for (int i=0;i<listRequest.size();i++) {
			BuyRequest br = listRequest.get(i);
			if (!br.getBuyer().equals(email)) {
				listRequest.remove(br);
			}
		}
		return listRequest;

	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/request/{requestId}")
	public BuyRequest getRequest(@RequestHeader("authorization") String header, @PathVariable(value = "requestId") Long requestId)throws IOException {
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	String email = tokenUser;

    	User user = userRepository.findById(email)
				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", email)));
	
    	BuyRequest br = buyRequestRepository.findById(requestId).orElseThrow(() -> new RecordNotFoundException(String.format("Request %d not found", requestId)));
		boolean grantUser = false;
		String buyer = br.getBuyer().getEmail();
		String seller = br.getSeller().getEmail();
    	if (tokenUser.equals(buyer))
    	{
    		grantUser=true;
    	}
    	if (tokenUser.equals(seller))
		{
    		grantUser=true;
    	}
    	if (grantUser==false) {
    		throw new AuthenticationErrorException("Failed to authenticate");
    	}
		return br;
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping("/request")
	public BuyRequest updateRequest(@RequestHeader("authorization") String header, @RequestBody Request request) throws IOException {
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	String buyerEmail = request.getBuyer();
    	String sellerEmail = request.getSeller();
		Long productId = request.getProductId();
		Long requestId = request.getRequestId();
    	
		if (tokenUser.equals(sellerEmail )) 
    	{
    			User buyer = userRepository.findById(buyerEmail)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("Buyer %s not found", buyerEmail)));
    			
    			User seller = userRepository.findById(sellerEmail)
        				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", sellerEmail)));
    			
    			Product product = productRepository.findById(productId)
    					.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found", productId)));

    			BuyRequest buyRequest = buyRequestRepository.findById(requestId)
    					.orElseThrow(() -> new RecordNotFoundException(String.format("Request %d not found", requestId)));
    			
    			buyRequest.setPrice(request.getPrice());
    			buyRequest.setStatus(request.getStatus());
    			
    			return buyRequestRepository.save(buyRequest);
		} 
    	else {
			throw new AuthenticationErrorException("Failed to authenticate");
    	}
	}
	@CrossOrigin(origins = "*")
	@DeleteMapping("/request")
	public BuyRequest deleteRequest(@RequestHeader("authorization") String header, @RequestBody Request request) throws IOException {
    	// Solo dejamos dar de alta productos asociados al usuario autenticado
    	RetroexchangesAuthorizationFilter authorization = new RetroexchangesAuthorizationFilter();
    	Claims claims = authorization.decodeToken(header);
    	String tokenUser = claims.getSubject();
    	
    	String buyerEmail = request.getBuyer();
    	String sellerEmail = request.getSeller();
		Long productId = request.getProductId();
		Long requestId = request.getRequestId();
    	
		boolean grantUser = false;
		if (tokenUser.equals(sellerEmail)) {
			grantUser = true;
		}
		if (tokenUser.equals(buyerEmail)) {
			grantUser = true;
		}
		if (grantUser) 
    	{
    			User buyer = userRepository.findById(buyerEmail)
    				.orElseThrow(() -> new RecordNotFoundException(String.format("Buyer %s not found", buyerEmail)));
    			User seller = userRepository.findById(sellerEmail)
        				.orElseThrow(() -> new RecordNotFoundException(String.format("User %s not found", sellerEmail)));
    			
    			Product product = productRepository.findById(productId)
    					.orElseThrow(() -> new RecordNotFoundException(String.format("Product %d not found", productId)));

    			BuyRequest buyRequest = buyRequestRepository.findById(requestId)
    					.orElseThrow(() -> new RecordNotFoundException(String.format("Request %d not found", requestId)));
    			
    			if ((buyRequest.getStatus()==RequestStatus.DENIED)|| 
    				(buyRequest.getStatus()==RequestStatus.PENDING)){
    			buyRequestRepository.delete(buyRequest);
    			return buyRequest;
   				}else{
   					throw new ForbidenResourceException("Forbidden operation");
   					
   				}
   			
			} 
    		else {
				throw new AuthenticationErrorException("Failed to authenticate");
    		}
		}
	}
	
