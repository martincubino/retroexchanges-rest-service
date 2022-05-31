package com.retroexchanges.rest.json;


import com.retroexchanges.rest.model.Rating;
import com.retroexchanges.rest.model.User;

import java.io.Serializable;
import java.util.Date;


public class RatingSecure implements Serializable {
    
	private static final long serialVersionUID = -7080239770857797910L;

	public RatingSecure() {
		
	}
	
	public RatingSecure (Rating rating) {
		super();
		this.setUserWhoRate(rating.getUserWhoRate());
		this.setUserRated(rating.getUserRated());
		this.setBuyRequest(rating.getUserRating().getBuyRequestId());
		this.rating = rating.getRating();
		
		this.setUserWhoRateName(rating.getUserWhoRate());
		this.setUserWhoRateSurname(rating.getUserWhoRate());
		this.setUserRatedName(rating.getUserRated());
		this.setUserRatedSurname(rating.getUserRated());
				
		this.setCreateAt(rating.getCreateAt());
		
	}
	
    private String userRated;
    private String userRatedName;
    private String userRatedSurname;
    private String userWhoRate;
    private String userWhoRateName;
    private String userWhoRateSurname;
    private Long buyRequest; 
    private Double rating;
    private Date createAt;
    
	public String getUserRated() {
		return this.userRated;
	}
	public String getUserRatedName() {
		return this.userRatedName;
	}
	public String getUserRatedSurname() {
		return this.userRatedSurname;
	}
	public void setUserRated(User userRated) {
		this.userRated = userRated.getEmail();
	}
	public void setUserRatedName(User userRated) {
		this.userRatedName = userRated.getName();
	}
	public void setUserRatedSurname(User userRated) {
		this.userRatedSurname = userRated.getSurname();
	}
	public String getUserWhoRate() {
		return this.userWhoRate;
	}
	public String getUserWhoRateName() {
		return this.userWhoRateName;
	}
	public String getUserWhoRateSurname() {
		return this.userWhoRateSurname;
	}
	public void setUserWhoRate(User userWhoRate) {
		this.userWhoRate = userWhoRate.getEmail();
	}
	public void setUserWhoRateName(User userWhoRate) {
		this.userWhoRateName = userWhoRate.getName();
	}
	public void setUserWhoRateSurname(User userWhoRate) {
		this.userWhoRateSurname = userWhoRate.getSurname();
	}
	public Double getRating() {
		return rating;
	}
	public void setBuyRequest(Long buyRequest) {
		this.buyRequest = buyRequest;
	}
	public Long getBuyRequest() {
		return this.buyRequest;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public Date getCreateAt() {
		return this.createAt;
	}
	
	public void setCreateAt(Date createAt) {
		this.createAt= createAt;
	}
    
}
