package com.retroexchanges.rest.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class RatingPK implements Serializable{
	
	private static final long serialVersionUID = 7307081130724186514L;
	
	public RatingPK() {
		
	}
	public RatingPK(String userWhoRate, String userRated, Long requestId) {
		this.userRated = userRated;
		this.userWhoRate = userWhoRate;
		this.buyRequestId = requestId;
	}

	public String getUserRated() {
		return userRated;
	}

	public void setUserRated(String userRated) {
		this.userRated = userRated;
	}

	public String getUserWhoRate() {
		return userWhoRate;
	}

	public void setUserWhoRate(String userWhoRate) {
		this.userWhoRate = userWhoRate;
	}

	public Long getBuyRequestId() {
		return buyRequestId;
	}

	public void setBuyRequestId(Long buyRequestId) {
		this.buyRequestId = buyRequestId;
	}

	private String userRated;
    private String userWhoRate;
    private Long buyRequestId;

		
}
