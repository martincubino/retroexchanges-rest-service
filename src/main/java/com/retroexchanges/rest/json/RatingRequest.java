package com.retroexchanges.rest.json;

import java.io.Serializable;

public class RatingRequest implements Serializable {

	private static final long serialVersionUID = 7307081130724186514L;

	public RatingRequest() {

	}

	public RatingRequest(String userRated, String userWhoRate, Long requestId) {
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

	public Double getRating() {
		return this.rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	private String userRated;
	private String userWhoRate;
	private Long buyRequestId;
	private Double rating;

}
