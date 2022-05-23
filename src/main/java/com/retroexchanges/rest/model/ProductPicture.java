package com.retroexchanges.rest.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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

	private Long productId;

	@Lob
	private byte[] picture;

	public Long getProductId() {
		return this.productId;
	}

	public Long getId() {
		return pictureId;
	}

	public void setId(Long id) {
		this.pictureId = id;
	}

	

	public void setProductId(Long productId) {
		this.productId= productId;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}
