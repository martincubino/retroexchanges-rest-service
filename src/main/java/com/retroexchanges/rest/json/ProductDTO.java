package com.retroexchanges.rest.json;

import java.io.Serializable;
import com.retroexchanges.rest.enumeration.*;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = -5533257383702041988L;

    private String name;
    private String description;
    private String owner;
    private Double price;
    private Long categoryId;
    
    private ProductStatus status;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name= name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
    public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public ProductStatus getStatus() {
		return this.status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}
}
