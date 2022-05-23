package com.retroexchanges.rest.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.io.Serializable;

/**
 * Created by fjmartincubino
 */
@Entity
@Table(name = "ratings")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt", "updatedAt"},
allowGetters = true)
public class Rating implements Serializable {
    
	private static final long serialVersionUID = -1080239770857797919L;

	public Rating() {
		
	}
	
	public Rating (String userRated, String userWhoRate, Long buyRequestId) {
		super();
		this.userRating = new RatingPK(userRated,userWhoRate,buyRequestId);
	}
	
	public RatingPK getUserRating() {
	    return userRating;
    }

	@EmbeddedId
	private RatingPK userRating;
	
	@ManyToOne()
	@MapsId("email")
    @JoinColumn(name="userRated")
    private User userRated;
	
	@MapsId("email")
    @ManyToOne()
    @JoinColumn(name="userWhoRate")
    private User userWhoRate;
	
	@MapsId("requestId")
    @ManyToOne()
    @JoinColumn(name="buyRequestId")
    private BuyRequest buyRequest;
		
    @NotBlank
    private Double rating;
    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createAt;

	public User getUserRated() {
		return userRated;
	}
	public void setUserRated(User userRated) {
		this.userRated = userRated;
	}
	public User getUserWhoRate() {
		return userWhoRate;
	}
	public void setUserWhoRate(User userWhoRate) {
		this.userWhoRate = userWhoRate;
	}
	public BuyRequest getBuyRequest() {
		return buyRequest;
	}
	public void setBuyRequest(BuyRequest buyRequest) {
		this.buyRequest = buyRequest;
	}
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
    
}
