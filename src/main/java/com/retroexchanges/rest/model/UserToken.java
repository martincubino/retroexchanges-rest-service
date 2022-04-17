package com.retroexchanges.rest.model;

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
@Entity
@Table(name = "user_tokens")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt"},
allowGetters = true)
public class UserToken implements Serializable{
	
	private static final long serialVersionUID = -234788421164359378L;

	@Id
	@Column(length = 200)
    private String token;

	@NotBlank
	@Column(length = 500)
    private String email;
	    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createAt;

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

}
