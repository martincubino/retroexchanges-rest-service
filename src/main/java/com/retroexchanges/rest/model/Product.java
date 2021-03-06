package com.retroexchanges.rest.model;

import com.retroexchanges.rest.enumeration.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
/**
 * Created by fjmartincubino
 */
@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createAt", "updatedAt"},
allowGetters = true)
public class Product implements Serializable {

	private static final long serialVersionUID = -8033257383702041988L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    private String name;

	@NotBlank
    private String description;
    
    @NotBlank
    private String owner;
    
    private Double price;
    
    @OneToOne()
    @JoinColumn(name="category_id")
    private Category category;
    
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="productId")
    @OrderBy("pictureId ASC")
    private List<ProductPicture> productPictures;
	
	public List<ProductPicture> getPictureList() {
		return productPictures;
	}

	public void setPictureList(List<ProductPicture> productPictures) {
		this.productPictures = productPictures;
	}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
    
    public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public ProductStatus getStatus() {
		return this.status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}
	public Date getCreateAt() {
		return this.createAt;
	}
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

    
}
