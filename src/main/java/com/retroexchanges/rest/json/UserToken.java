package com.retroexchanges.rest.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retroexchanges.rest.enumeration.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.io.Serializable;


/**
 * Created by fjmartincubino
 */

public class UserToken implements Serializable{
	
	private static final long serialVersionUID = -234788421164359378L;

    private String token;

    private String email;
	    
    private Date createAt;
    
    private Date expirateAt;
    
    private boolean isAdmin;

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public Date getCreateAt() {
		return this.createAt;
	}
	
	public void setExpirateAt(Date expirateAt) {
		this.expirateAt = expirateAt;
	}
	
	public Date getExpirateAt() {
		return this.expirateAt;
	}
	
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin=isAdmin;;
	}
	
	public boolean getIsAdmin() {
		return this.isAdmin;
	}

}
