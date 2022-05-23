package com.retroexchanges.rest.json;

import java.util.Date;
import java.io.Serializable;


/**
 * Created by fjmartincubino
 */

public class UserRating implements Serializable{
	
	private static final long serialVersionUID = -23478842116447078L;

    private String email;
    private Double rating;

    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Double getRating () {
		return this.rating;
	}
	public void setRating (Double rating) {
		this.rating = rating;
	}

}
