package com.retroexchanges.rest.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retroexchanges.rest.enumeration.*;
import com.retroexchanges.rest.model.User;
import com.retroexchanges.rest.enumeration.UserStatus;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.io.Serializable;


public class UserSecure implements Serializable{
	
	private static final long serialVersionUID = -2216788421164359668L;

	private String email;
    
    private String name;

	private String surname;
    
    private Date createAt;

    private Date updatedAt;
    
    private Double rating;
    
    private String nif;
    
    private String address;
    
    private UserStatus status;
    
    public UserSecure(User user) {
    	this.setEmail(user.getEmail());
    	this.setName(user.getName());
    	this.setSurname(user.getSurname());
    	this.setRating(user.getRating());
    	this.setNif(user.getNif());
    	this.setAddress(user.getAddress());
    	this.setStatus(user.getStatus());
    	this.setCreateAt(user.getCreateAt());
    	this.setUpdatedAt(user.getUpdatedAt());
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }
    
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif= nif;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public void setRating(Double rating) {
	 	this.rating = rating;
	}	
	
	public Double getRating() {
	 	return this.rating;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdatedAt() {
		return this.updatedAt;
	}	
	public Date getCreateAt() {
		return this.createAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}	
}
