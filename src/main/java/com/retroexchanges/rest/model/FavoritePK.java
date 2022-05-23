package com.retroexchanges.rest.model;

import javax.persistence.*;

import java.io.Serializable;

@Embeddable
public class FavoritePK implements Serializable {
	
	private static final long serialVersionUID = 6978159596380874333L;
	public FavoritePK() {
		
	}
	public FavoritePK(String email,Long productId) {
		this.email = email;
		this.productId = productId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	private Long productId;
    private String email;
    
	
}
