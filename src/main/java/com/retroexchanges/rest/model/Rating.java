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
@JsonIgnoreProperties(value = {"userRating","createAt", "updatedAt"},
allowGetters = true)
public class Rating implements Serializable {
    
	private static final long serialVersionUID = -1080239770857797919L;

	public Rating() {
		
	}
	
	public Rating (User userWhoRate, User userRated, BuyRequest buyRequest) {
		super();
		this.userRating = new RatingPK(userWhoRate.getEmail(),userRated.getEmail(), buyRequest.getRequestId());
		this.userWhoRate = userWhoRate;
		this.userRated = userRated;
		this.buyRequest = buyRequest; 
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
		
    private Double rating;
    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createAt;

	public User getUserRated() {
		return this.userRated;
	}
	public void setUserRated(User userRated) {
		this.userRated = userRated;
	}
	public User getUserWhoRate() {
		return this.userWhoRate;
	}
	public void setUserWhoRate(User userWhoRate) {
		this.userWhoRate = userWhoRate;
	}
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Date getCreateAt() {
		return this.createAt;
	}

    
}
