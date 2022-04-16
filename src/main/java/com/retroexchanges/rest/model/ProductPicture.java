package com.retroexchanges.rest.model;

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
@Table(name = "product_pictures")
@EntityListeners(AuditingEntityListener.class)
public class ProductPicture implements Serializable {
	
	private static final long serialVersionUID = 8687436041256230101L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;

	@ManyToOne()
    @MapsId("product")
    @JoinColumn(name="productId")
    private Product product;

    @NotBlank
    private Byte[] picture;
    
    public Product getProduct() {
        return product;
    }

    public Long getId() {
        return pictureId;
    }

    public void setId(Long id) {
        this.pictureId = id;
    }

    public Product getProductId() {
        return product;
    }

    public void setProductId(Product product) {
        this.product = product;
    }

	public Byte[] getPicture() {
		return picture;
	}

	public void setPicture(Byte[] picture) {
		this.picture = picture;
	}

    
}
