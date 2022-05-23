package com.retroexchanges.rest.json;

import com.retroexchanges.rest.enumeration.*;

import java.io.Serializable;

/**
 * Created by fjmartincubino
 */
public class Request implements Serializable{
    
	private static final long serialVersionUID = -2765263333343436564L;

    private Long requestId;
	private String buyer;
    private String seller;
    
    private Long productId;
    
    private Double price;
    
    private RequestStatus status;
    
	public String getBuyer() {
		return this.buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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
	
	public Long getRequestId() {
		return this.requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
   
}
