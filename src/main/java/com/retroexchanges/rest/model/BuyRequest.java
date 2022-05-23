package com.retroexchanges.rest.model;

import com.retroexchanges.rest.enumeration.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "buy_request")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt", "updatedAt"},
allowGetters = true)
public class BuyRequest implements Serializable{
    
	private static final long serialVersionUID = -2765263333343436707L;

	public BuyRequest() {
		
	}
	public BuyRequest(User buyer, User seller, Product product){
		this.buyer = buyer;
		this.seller = seller;
		this.product = product;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

	@OneToOne()
    @JoinColumn(name="buyer")
    private User buyer;

	@OneToOne()
    @JoinColumn(name="seller")
    private User seller;
    
	@OneToOne()
    @JoinColumn(name="productId")
    private Product product;
	
    private Double price;
    
    private RequestStatus status;
    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
    

    public Long getId() {
        return requestId;
    }

    public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public User getBuyer() {
		return this.buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getSeller() {
		return this.seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setId(Long requestId) {
        this.requestId = requestId;
    }

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	public Date getCreateAt() {
		return this.createAt;
	}
	public Date getUpdatedAt() {
		return this.updatedAt;
	}
    
}
