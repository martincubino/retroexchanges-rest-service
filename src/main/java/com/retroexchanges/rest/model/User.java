package com.retroexchanges.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retroexchanges.rest.enumeration.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.io.Serializable;


/**
 * Created by fjmartincubino
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"isAdmin","ratings","createAt", "updatedAt"},
allowGetters = true)
public class User implements Serializable{
	
	private static final long serialVersionUID = -2216788421164359378L;

	@Id
	@Column(length = 200)
    private String email;
    
    @NotBlank
    private String name;

	@NotBlank
    private String surname;
    
    private String address;
    
    private String password;
    
    private String nif;
    
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
    
    private boolean isAdmin = false;
    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="userRated")
    @Column(insertable = false, updatable = false)
    private List<Rating> ratings;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public void setIsAdmin(boolean isadmin) {
		this.isAdmin= isadmin;
	}
	
	public boolean getIsAdmin() {
		return this.isAdmin;
	}
	
	public Double getRating() {
		Double rating = 0.0;
		int elements = ratings.size();
		for (int i=0;i<elements;i++) 
		{
			rating+=ratings.get(i).getRating();
		}
		rating = rating / elements; 
		return rating ;
	}
	public Date getCreateAt() {
		return this.createAt;
	}
	public Date getUpdatedAt() {
		return this.updatedAt;
	}
	

}
